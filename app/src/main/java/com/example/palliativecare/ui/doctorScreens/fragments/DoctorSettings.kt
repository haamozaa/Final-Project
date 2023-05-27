package com.example.palliativecare.ui.doctorScreens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palliativecare.databinding.DoctorSettingsBinding

class DoctorSettings : Fragment() {
    private lateinit var _binding : DoctorSettingsBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DoctorSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }
}