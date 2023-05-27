package com.example.palliativecare.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.palliativecare.R
import com.example.palliativecare.databinding.S2Binding


class S2 : Fragment() {
    private lateinit var _binding : S2Binding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = S2Binding.inflate(layoutInflater,container,false)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.s3)
        }
        return binding.root
    }
}