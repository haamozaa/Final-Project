package com.example.palliativecare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.palliativecare.databinding.SplashBinding
import com.example.palliativecare.languages.BaseActivity
import com.example.palliativecare.ui.doctorScreens.DoctorContainer
import com.example.palliativecare.ui.patientScreens.PatientContainer
import com.example.palliativecare.ui.splash.SplashContainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zeugmasolutions.localehelper.Locales
import java.util.*

class Splash : BaseActivity() {
    private lateinit var binding:SplashBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore
        if (Locale.getDefault().language != "ar"){
            updateLocale(Locales.Arabic)
        }
        Handler().postDelayed({
            if (auth.currentUser == null){
                startActivity(Intent(this,SplashContainer::class.java))
                finish()
            }else{
                getTypeOfUser()
            }
        },0)
    }
    private fun getTypeOfUser(){
        db.collection("doctors").whereEqualTo("userAuthId",auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.size() == 1){
                startActivity(Intent(this,DoctorContainer::class.java))
            }else{
                startActivity(Intent(this,PatientContainer::class.java))
            }
            finish()
        }
    }
}