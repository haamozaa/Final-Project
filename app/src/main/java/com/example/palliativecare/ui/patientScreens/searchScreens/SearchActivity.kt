package com.example.palliativecare.ui.patientScreens.searchScreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palliativecare.databinding.SearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding : SearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}