package com.example.palliativecare.ui.patientScreens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palliativecare.R
import com.example.palliativecare.databinding.PatientSettingsBinding

class PatientSettings : Fragment() {
    private lateinit var _binding : PatientSettingsBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = PatientSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }
}