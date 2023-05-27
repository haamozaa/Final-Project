package com.example.palliativecare.data

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import com.example.palliativecare.model.Doctor
import com.example.palliativecare.model.Message
import com.example.palliativecare.model.Topic
import com.example.palliativecare.model.User
import com.example.palliativecare.notification.NotificationsData
import com.example.palliativecare.notification.PushNotification
import com.example.palliativecare.notification.api.ApiUtilities
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DataBase(val context:Context){
    var auth = Firebase.auth
    var db = Firebase.firestore
    var storage = Firebase.storage
    var storageRef = storage.reference
    var realTimeDataBase = FirebaseDatabase.getInstance()
    var analytics = Firebase.analytics
    val messaging = FirebaseMessaging.getInstance()
    var sharedPreferences = context.getSharedPreferences("PalliativeCare",Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()


    private lateinit var imageLink : String
    private lateinit var pdfLink : String
    private lateinit var cameTopics : ArrayList<Topic>



    var onSavedUser:((Boolean) -> Unit)? = null
    var isCorrect : ((Boolean,Boolean) -> Unit)? = null
    var onSavedTopic : ((Boolean) -> Unit)? = null
    var onSavedUpdatedTopic : ((Boolean) -> Unit)? = null
    var onTopicsCame : ((ArrayList<Topic>) -> Unit)? = null
    var onTopicsDetailsCame : ((Topic) -> Unit)? = null
    var onDeleteTopic:((Boolean) -> Unit)? = null
    var onCategoryCame:((MutableMap<String,String>) -> Unit)? = null
    var onCategoryIdCame:((String) -> Unit)? = null
    var onUsersCame:((ArrayList<Doctor>) -> Unit)? = null
    var onMessagesCame:((ArrayList<Message>) -> Unit)? = null
    var onType:((Boolean) -> Unit)? = null


    private lateinit var collName : String
    @SuppressLint("SuspiciousIndentation")
    fun signUpUser(user:User, password:String, type:String){
        when(type){
            "مريض" -> {
                collName = "patients"
                editor.putBoolean("isPatient",true)
                editor.commit()

            }
            "طبيب" -> {
                collName = "doctors"
                editor.putBoolean("isPatient",false)
                editor.commit()

            }

        }
        messaging.token.addOnCompleteListener { result ->
            auth.createUserWithEmailAndPassword(user.email!!,password).addOnSuccessListener{
                val userId = it.user!!.uid
                val  newUser = hashMapOf("fName" to user.fName,"sName" to user.sName,"lName" to user.lName,"userType" to type,"email" to user.email,"dob" to user.dOB,"address" to user.address,"userAuthId" to userId,"category" to user.category,"tokenFcm" to result.result)

                db.collection(collName).add(newUser).addOnSuccessListener{
                    onSavedUser?.invoke(true)
                }.addOnFailureListener {
                    auth.currentUser?.delete()
                    onSavedUser?.invoke(false)
                }
                editor.putString("name","${user.fName} ${user.lName}")
                editor.commit()
            }.addOnFailureListener {
                Toast.makeText(context,it.message.toString(),Toast.LENGTH_LONG).show()
                onSavedUser?.invoke(false)
            }
        }
    }
    fun loginUser(email:String,password: String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { it ->
            if (it.isSuccessful){
             db.collection("doctors").whereEqualTo("userAuthId",auth.currentUser?.uid).get().addOnSuccessListener { query ->
                 if (query.size() == 1){
                     messaging.token.addOnCompleteListener {
                         if (it.isSuccessful){
                             db.collection("doctors").document(query.toList()[0].id).update("tokenFcm",it.result).addOnCompleteListener {
                                 db.collection("doctors").document(query.toList()[0].id).get().addOnSuccessListener { doc ->
                                     editor.putString("name","${doc["fName"]} ${doc["lName"]}")
                                     editor.putBoolean("isPatient",false)
                                     editor.commit()
                                 }
                                 analytics.logEvent(FirebaseAnalytics.Event.LOGIN){
                                     param(FirebaseAnalytics.Param.ITEM_ID,auth.currentUser!!.uid)
                                     param(FirebaseAnalytics.Param.ITEM_NAME,"doctor")
                                 }
                                 isCorrect?.invoke(true,true)
                             }

                         }
                     }

                 }else{
                     db.collection("patients").whereEqualTo("userAuthId",auth.currentUser?.uid).get().addOnSuccessListener { userQ ->
                         if (userQ.size() == 1){
                             messaging.token.addOnCompleteListener {
                                 if (it.isSuccessful){
                                     db.collection("patients").document(userQ.toList()[0].id).update("tokenFcm",it.result).addOnCompleteListener {
                                         db.collection("patients").document(userQ.toList()[0].id).get().addOnSuccessListener { doc ->
                                             editor.putString("name","${doc["fName"]} ${doc["lName"]}")
                                             editor.putBoolean("isPatient",true)
                                             editor.commit()
                                         }
                                         isCorrect?.invoke(true,false)
                                         analytics.logEvent(FirebaseAnalytics.Event.LOGIN){
                                             param(FirebaseAnalytics.Param.ITEM_ID,auth.currentUser!!.uid)
                                             param(FirebaseAnalytics.Param.ITEM_NAME,"patient")
                                     }
                                 }
                             }
                         }
                     }
                     }
                 }

             }
         }else{
             isCorrect?.invoke(false,false)
         }
        }
    }
    fun saveTopic(imagePath : String,pdfPath : String,title:String,topic:String,categoryId:String){
        val a = storageRef.child(auth.currentUser!!.uid + "/Files").child(System.currentTimeMillis().toString())
        a.putFile(imagePath.toUri()).addOnSuccessListener {
            a.downloadUrl.addOnSuccessListener { ita ->
                imageLink = ita.toString()
                val b = storageRef.child(auth.currentUser!!.uid + "/Files").child((System.currentTimeMillis() + 5000).toString())
                b.putFile(pdfPath.toUri()).addOnSuccessListener {
                    b.downloadUrl.addOnSuccessListener {itb ->
                        pdfLink = itb.toString()
                        val topicD= hashMapOf("doctorId" to auth.currentUser!!.uid,"topicTitle" to title,"topic" to topic,"imageLink" to imageLink,"pdfLink" to pdfLink,"categoryId" to categoryId)
                        db.collection("Topics").add(topicD).addOnSuccessListener {
                            onSavedTopic?.invoke(true)
                        }
                    }

                }.addOnFailureListener{
                    onSavedTopic?.invoke(false)
                }
            }

        }.addOnFailureListener{
            onSavedTopic?.invoke(false)

        }
    }
    fun updateTopic(topicId:String ,title: String,topic:String){
        val data = mutableMapOf<String,Any>("topicTitle" to title,"topic" to topic)
        db.collection("Topics").document(topicId).update(data).addOnCompleteListener {
            if (it.isSuccessful){
                onSavedUpdatedTopic!!.invoke(true)
            }else{
                onSavedUpdatedTopic!!.invoke(false)
            }
        }.addOnCanceledListener {
            onSavedUpdatedTopic!!.invoke(false)
        }
    }
    fun getTopics(id:String?){
        var doctorId = auth.currentUser!!.uid
        if (id != null){
            doctorId = id
        }
        db.collection("Topics").whereEqualTo("doctorId",doctorId).get().addOnSuccessListener { topics ->
            if (topics.size() > 0){
                cameTopics = arrayListOf()
                for (i in topics){
                    cameTopics.add(Topic(auth.currentUser!!.uid,i["topicTitle"].toString(),i["topic"].toString(),i["imageLink"].toString(),i["pdfLink"].toString(),i.id))
                }
                if(cameTopics.size == topics.size()){
                    onTopicsCame!!.invoke(cameTopics)
                }
            }
        }
    }

    fun getTopicDetails(topicId : String){
        db.collection("Topics").document(topicId).get().addOnSuccessListener {
            if (it.exists()){
                val topic = Topic(auth.currentUser!!.uid,it["topicTitle"].toString(),it["topic"].toString(),it["imageLink"].toString(),it["pdfLink"].toString(),topicId)
                onTopicsDetailsCame!!.invoke(topic)
            }
        }
    }
    fun deleteTopic(topic : Topic){
        val pdfLink = FirebaseStorage.getInstance().getReferenceFromUrl(topic.topicPdf!!).name
        val imageLink = FirebaseStorage.getInstance().getReferenceFromUrl(topic.topicImage!!).name

        storageRef.child(auth.currentUser!!.uid + "/Files").child(pdfLink).delete().addOnCompleteListener { pdfTask ->
            if (pdfTask.isSuccessful){
                storageRef.child(auth.currentUser!!.uid + "/Files").child(imageLink).delete().addOnCompleteListener {imageTask ->
                    if (imageTask.isSuccessful){
                        db.collection("Topics").document(topic.topicId!!).delete().addOnCompleteListener {
                            onDeleteTopic!!.invoke(true)
                        }
                    }
                }

            }
        }
    }
    fun getCategorise(){
        val categorise = mutableMapOf<String,String>()
        db.collection("Categorise").get().addOnSuccessListener{ snapShot ->
            if (snapShot.size() >0){
                for (i in snapShot){
                    categorise[i.id] = i["name"].toString()
                }
                onCategoryCame!!.invoke(categorise)
            }
        }.addOnFailureListener{
            onCategoryCame!!.invoke(categorise)
        }
    }
    fun getAllUsers(iAm:String, they:String){
        db.collection(iAm).whereEqualTo("userAuthId",auth.currentUser!!.uid).get().addOnSuccessListener { snapShot ->
            var categoryId = ""
            if(snapShot.size() > 0){
                for(i in snapShot){
                    categoryId = i["category"].toString()
                }
                if (categoryId != "") {
                    db.collection(they).whereEqualTo("category",categoryId).get().addOnSuccessListener { snapShotD ->
                        if(snapShotD.size() > 0){
                            val doctors = arrayListOf<Doctor>()
                            for (i in snapShotD){
                                val fullName = "${i["fName"]} ${i["lName"]}"
                                doctors.add(Doctor(fullName,i["userAuthId"].toString(),i["tokenFcm"].toString()))
                            }
                            onUsersCame!!.invoke(doctors)
                        }
                    }
                }
            }
        }
    }

    fun getCategoryId(){
        db.collection("doctors").whereEqualTo("userAuthId",auth.currentUser!!.uid).get().addOnSuccessListener { snapshot ->
            if (snapshot.size() > 0){
                val category = snapshot.toList()[0]["category"].toString()
                onCategoryIdCame!!.invoke(category)
            }
        }

    }
    fun getChatUser(senderRoom:String){
        val messages = arrayListOf<Message>()
        realTimeDataBase.reference.child("chats").child(senderRoom).child("message").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                messages.clear()
                if (messages.size == 0){
                    for (item in snapshot.children){
                        val message : Message? = item.getValue(Message::class.java)
                        message!!.messageId = item.key
                        messages.add(message)
                    }

                    onMessagesCame!!.invoke(messages)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun sendMessage(message: Message,date:Date,senderRoom : String,receiverRoom : String,senderName:String,receiverToken: String) {
        val randomKey = realTimeDataBase.reference.push().key
        val lastMessageObj = HashMap<String,Any>()
        lastMessageObj["lastMessage"] = message.message!!
        lastMessageObj["lastMessageTime"] = date.time
        realTimeDataBase.reference.child("chats").child(senderRoom).updateChildren(lastMessageObj)
        realTimeDataBase.reference.child("chats").child(receiverRoom).updateChildren(lastMessageObj)
        realTimeDataBase.reference.child("chats").child(senderRoom).child("message").child(randomKey!!).setValue(message).addOnSuccessListener {
            realTimeDataBase.reference.child("chats").child(receiverRoom).child("message").child(randomKey).setValue(message).addOnSuccessListener {
                sendNotification(message.message!!,senderName,receiverToken)
            }
        }

    }

    private fun sendNotification(messageMsg: String, senderName: String,receiverToken: String) {
        val notification = PushNotification(NotificationsData("رسالة من ${sharedPreferences.getString("name","أحد الأشخاص")}",messageMsg),receiverToken)
        ApiUtilities.getInstance().sendNotification(notification).enqueue(object : Callback<PushNotification>{
            override fun onResponse(
                call: Call<PushNotification>,
                response: Response<PushNotification>
            ) {
            }

            override fun onFailure(call: Call<PushNotification>, t: Throwable) {}
        })

    }

    fun getType() : Boolean{
        return sharedPreferences.getBoolean("isPatient",false)
    }
}


