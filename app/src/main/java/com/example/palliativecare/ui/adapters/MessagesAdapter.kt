package com.example.palliativecare.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.R
import com.example.palliativecare.databinding.ReceiveMessageItemBinding
import com.example.palliativecare.databinding.SendMessageItemBinding
import com.example.palliativecare.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class MessagesAdapter (
    val context: Context,
    messages: ArrayList<Message>?,
    senderRoom : String,
    receiverRoom : String
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    lateinit var messages: ArrayList<Message>
    val ITEM_SENT = 0
    val ITEM_RECEIVED = 1
    lateinit var senderRoom :String
    lateinit var receiverRoom :String


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT){
            val view = LayoutInflater.from(context).inflate(R.layout.send_message_item,parent,false)
            SentMessageHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.receive_message_item,parent,false)
            ReceivedMessageHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messages[position]
        return if (FirebaseAuth.getInstance().uid == currentMessage.senderId){
            ITEM_SENT
        }else{
            ITEM_RECEIVED
        }
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder.javaClass == SentMessageHolder::class.java){
            val viewHolder = holder as SentMessageHolder
            if (message.message.equals("photo")){
                viewHolder.binding.image.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.mLin.visibility = View.GONE
                Picasso.get().load(message.imageUrl).into(holder.binding.image)

            }
            viewHolder.binding.time.text = SimpleDateFormat("HH:mm").format(message.timeStamp).toString()
            viewHolder.binding.message.text = message.message
        }else{
            val viewHolder = holder as ReceivedMessageHolder
            if (message.message.equals("photo")){
                viewHolder.binding.image.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.mLin.visibility = View.GONE
                Picasso.get().load(message.imageUrl).into(holder.binding.image)

            }
            viewHolder.binding.message.text = message.message
            viewHolder.binding.time.text = SimpleDateFormat("hh:mm").format(message.timeStamp).toString()

        }
    }
    override fun getItemCount(): Int = messages.size


    inner class SentMessageHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding = SendMessageItemBinding.bind(itemView)
    }
    inner class ReceivedMessageHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding = ReceiveMessageItemBinding.bind(itemView)
    }
    init {
        if (messages != null){
            this.messages = messages
        }else{
            this.senderRoom = senderRoom
            this.receiverRoom = receiverRoom
        }
    }


}