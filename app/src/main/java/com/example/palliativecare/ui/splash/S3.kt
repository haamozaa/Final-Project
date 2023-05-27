package com.example.palliativecare.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palliativecare.R
import com.example.palliativecare.databinding.S3Binding
import com.example.palliativecare.ui.registration.RegistrationContainer

class S3 : Fragment() {
   private lateinit var _binding : S3Binding
   private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = S3Binding.inflate(layoutInflater,container,false)
        binding.next.setOnClickListener {
            startActivity(Intent(requireContext(),RegistrationContainer::class.java))
            requireActivity().finish()
        }
        return binding.root
    }
}