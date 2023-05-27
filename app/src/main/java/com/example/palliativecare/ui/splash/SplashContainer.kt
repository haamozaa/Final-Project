package com.example.palliativecare.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.databinding.SplashContainerBinding

class SplashContainer : AppCompatActivity() {
    private lateinit var binding:SplashContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}