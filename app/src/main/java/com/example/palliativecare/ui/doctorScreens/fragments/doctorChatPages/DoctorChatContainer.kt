package com.example.palliativecare.ui.doctorScreens.fragments.doctorChatPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.R
import com.example.palliativecare.databinding.DoctorChatContainerBinding
import com.example.palliativecare.databinding.DoctorContainerBinding

class DoctorChatContainer : AppCompatActivity() {
    private lateinit var binding : DoctorChatContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorChatContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}