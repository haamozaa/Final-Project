package com.example.palliativecare.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.palliativecare.R
import com.example.palliativecare.databinding.S1Binding
import com.example.palliativecare.databinding.SplashBinding


class S1 : Fragment() {
    private lateinit var _binding : S1Binding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = S1Binding.inflate(layoutInflater,container,false)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.s2)
        }
        return binding.root
    }

}