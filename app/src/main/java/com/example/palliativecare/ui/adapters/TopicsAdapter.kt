package com.example.palliativecare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.databinding.TopicItemBinding
import com.example.palliativecare.model.Topic

class TopicsAdapter(private val isDoctor:Boolean, private val topics : ArrayList<Topic>) : RecyclerView.Adapter<TopicsAdapter.MyHolder>() {
    var onDelete : ((Topic) -> Unit)? = null
    var onEdit : ((Topic) -> Unit)? = null
    var onShowDetails : ((Topic) -> Unit)? = null
    inner class MyHolder(val binding : TopicItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = TopicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: MyHolder, position: Int){
        if (!isDoctor){
            holder.binding.deleteBtn.isVisible = false
            holder.binding.editBtn.isVisible = false
        }
        val currentTopic = topics[position]
        holder.binding.apply {
            title.text = currentTopic.topicTitle
            card.setOnClickListener {
                onShowDetails!!.invoke(currentTopic)
            }
            editBtn.setOnClickListener {
                onEdit!!.invoke(currentTopic)
            }
            deleteBtn.setOnClickListener{
                onDelete!!.invoke(currentTopic)
            }

        }
    }



}