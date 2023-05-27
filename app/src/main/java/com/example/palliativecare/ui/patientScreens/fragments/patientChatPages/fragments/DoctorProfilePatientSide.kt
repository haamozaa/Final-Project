package com.example.palliativecare.ui.patientScreens.fragments.patientChatPages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.palliativecare.databinding.DoctorProfilePatientSideBinding


class DoctorProfilePatientSide : Fragment() {
    private lateinit var _binding : DoctorProfilePatientSideBinding
    private val binding get() = _binding
    private val args by navArgs<DoctorProfilePatientSideArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DoctorProfilePatientSideBinding.inflate(inflater,container,false)
        if (args.doctor.id.isNotEmpty()){
            binding.apply {

            }
        }
        return binding.root
    }
}