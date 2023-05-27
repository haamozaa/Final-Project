package com.example.palliativecare.ui.patientScreens.fragments.doctorTopicsPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.example.palliativecare.R
import com.example.palliativecare.databinding.DoctorTopicsBinding
import io.grpc.NameResolver.Args

class DoctorTopics : AppCompatActivity() {
    private lateinit var binding : DoctorTopicsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorTopicsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}