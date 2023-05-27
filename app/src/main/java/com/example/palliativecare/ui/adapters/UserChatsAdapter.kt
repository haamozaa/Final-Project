package com.example.palliativecare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.databinding.ChatItemBinding
import com.example.palliativecare.model.Doctor

class UserChatsAdapter(private val users : ArrayList<Doctor>,var areTheyDoctor:Boolean) : RecyclerView.Adapter<UserChatsAdapter.MyHolder>() {

    var onClicked : ((Doctor,TextView,ImageView) -> Unit)? = null
    inner class MyHolder(var binding: ChatItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentUser = users[position]

        holder.binding.apply {
            if (!areTheyDoctor){
                image.isVisible = false
            }
            name.text = currentUser.name
            card.setOnClickListener {
                onClicked!!.invoke(currentUser,name,image)
            }

        }
    }

    override fun getItemCount(): Int = users.size

}