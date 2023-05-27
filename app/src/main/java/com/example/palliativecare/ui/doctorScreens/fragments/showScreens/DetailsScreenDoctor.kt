package com.example.palliativecare.ui.doctorScreens.fragments.showScreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.databinding.DetailsScreenDoctorBinding
class DetailsScreenDoctor : AppCompatActivity() {
    private lateinit var binding : DetailsScreenDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsScreenDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}