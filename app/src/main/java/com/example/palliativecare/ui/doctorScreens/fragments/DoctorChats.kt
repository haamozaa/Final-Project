package com.example.palliativecare.ui.doctorScreens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.DoctorChatsBinding
import com.example.palliativecare.ui.adapters.UserChatsAdapter
import com.example.palliativecare.ui.doctorScreens.fragments.doctorChatPages.DoctorChatContainer
import com.example.palliativecare.ui.patientScreens.fragments.patientChatPages.PatientChatContainer

class DoctorChats : Fragment() {
    private lateinit var _binding : DoctorChatsBinding
    private val binding get() = _binding
    private lateinit var db : DataBase
    private lateinit var checkInternet: CheckInternet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DoctorChatsBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())
        checkInternet = CheckInternet(requireContext(),binding.root)
        if (checkInternet.isConnectedBool()){
            db.getAllUsers("doctors","patients")
        }
        db.onUsersCame = {patients ->
            if (patients.size > 0){

                    val adapter = UserChatsAdapter(patients,false)
                    binding.patients.layoutManager = LinearLayoutManager(requireContext())
                    binding.patients.adapter = adapter
                adapter.onClicked = { doctor, name, _ ->
                    val intent = Intent(requireContext(), DoctorChatContainer::class.java)
                    intent.putExtra("id",doctor.id)
                    intent.putExtra("name",doctor.name)
                    intent.putExtra("token",doctor.token)
                    val option = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),androidx.core.util.Pair.create(name,"name"))
                    startActivity(intent,option.toBundle())
                }
            }
        }

        return binding.root
    }
}