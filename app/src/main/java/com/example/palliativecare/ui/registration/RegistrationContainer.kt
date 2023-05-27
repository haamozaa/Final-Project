package com.example.palliativecare.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.R
import com.example.palliativecare.databinding.RegistrationContainerBinding
import com.example.palliativecare.ui.registration.signing.LogIn

class RegistrationContainer : AppCompatActivity() {
    private lateinit var binding:RegistrationContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistrationContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}