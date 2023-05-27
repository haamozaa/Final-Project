package com.example.palliativecare.ui.patientScreens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.PatientChatsBinding
import com.example.palliativecare.ui.adapters.UserChatsAdapter
import com.example.palliativecare.ui.patientScreens.fragments.patientChatPages.PatientChatContainer


class PatientChats : Fragment() {
    private lateinit var _binding : PatientChatsBinding
    private val binding get() = _binding
    private lateinit var db : DataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = PatientChatsBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())
        db.getAllUsers("patients","doctors")



        db.onUsersCame = { doctors ->
            if (doctors.size > 0){
                val adapter = UserChatsAdapter(doctors,true)
                binding.contacts.layoutManager = LinearLayoutManager(requireContext())
                binding.contacts.adapter = adapter

                adapter.onClicked = {doctor,name,image ->
                    val intent = Intent(requireContext(),PatientChatContainer::class.java)
                    intent.putExtra("id",doctor.id)
                    intent.putExtra("name",doctor.name)
                    intent.putExtra("token",doctor.token)
                    val option = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),androidx.core.util.Pair.create(name,"name"),androidx.core.util.Pair.create(image,"image"))
                    startActivity(intent,option.toBundle())
                }
            }
        }


        return binding.root
    }
}