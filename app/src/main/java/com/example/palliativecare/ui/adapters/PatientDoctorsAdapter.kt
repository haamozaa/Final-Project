package com.example.palliativecare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.databinding.DoctorItemBinding
import com.example.palliativecare.model.Doctor

class PatientDoctorsAdapter(private val doctors:ArrayList<Doctor>): RecyclerView.Adapter<PatientDoctorsAdapter.MyHolder>(){

    var onDoctorClicked : ((Doctor) -> Unit)? = null
    inner class MyHolder (val binding:DoctorItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val binding = DoctorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentDoctor = doctors[position]
        holder.binding.apply {
            doctorName.text = currentDoctor.name
            card.setOnClickListener {
                onDoctorClicked!!.invoke(Doctor(currentDoctor.name,currentDoctor.id,currentDoctor.token))
            }
        }
    }

    override fun getItemCount(): Int = doctors.size
}