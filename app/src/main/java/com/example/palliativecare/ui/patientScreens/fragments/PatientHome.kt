package com.example.palliativecare.ui.patientScreens.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.PatientHomeBinding
import com.example.palliativecare.ui.adapters.PatientDoctorsAdapter
import com.example.palliativecare.ui.patientScreens.fragments.doctorTopicsPage.DoctorTopics
import com.example.palliativecare.ui.patientScreens.searchScreens.SearchActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage


class PatientHome : Fragment() {
    private lateinit var _binding : PatientHomeBinding
    private val binding get() = _binding
    private lateinit var db :DataBase
    private lateinit var checkInternet: CheckInternet
    private lateinit var adapter : PatientDoctorsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = PatientHomeBinding.inflate(inflater,container,false)

        checkInternet = CheckInternet(requireContext(),binding.root)
        db = DataBase(requireContext())
        if (checkInternet.isConnectedBool()){
            db.getAllUsers("patients","doctors")
        }
        binding.searchView.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),androidx.core.util.Pair.create(binding.searchView,"image_Big"))
            startActivity(intent,option.toBundle())
        }
        binding.searchBtn.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),androidx.core.util.Pair.create(binding.searchView,"image_Big"))
            startActivity(intent,option.toBundle())
        }

        db.onUsersCame = { doctors ->
            if (doctors.isNotEmpty()){
                adapter = PatientDoctorsAdapter(doctors)
                binding.doctors.layoutManager = GridLayoutManager(requireContext(),2)
                binding.doctors.setHasFixedSize(true)
                binding.doctors.adapter = adapter
                adapter.onDoctorClicked = {
                    val doctor = arrayListOf(it.id,it.name)
                    val intent = Intent(requireContext(), DoctorTopics()::class.java)
                    intent.putExtra("doctor",doctor)
                    startActivity(intent)
                }
            }
        }




        return binding.root
    }


}