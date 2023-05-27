package com.example.palliativecare.ui.patientScreens.fragments.patientChatPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.databinding.PatientChatContainerBinding

class PatientChatContainer : AppCompatActivity() {
    private lateinit var binding:PatientChatContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PatientChatContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}