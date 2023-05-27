package com.example.palliativecare.ui.registration.signing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.palliativecare.R
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.LoginBinding
import com.example.palliativecare.ui.doctorScreens.DoctorContainer
import com.example.palliativecare.ui.patientScreens.PatientContainer
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app


class LogIn : Fragment() {
    private lateinit var _binding : LoginBinding
    private val binding get() = _binding
    private lateinit var db : DataBase
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = LoginBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())
        analytics = Firebase.analytics
        binding.loginMotion.transitionToEnd()
        binding.btnLogin.setOnClickListener {
            val checkInternet = CheckInternet(requireContext(),binding.root)
            if (checkInternet.isConnectedBool()){
                binding.apply {
                    if (email.text!!.isNotEmpty() && password.text!!.isNotEmpty()){
                        binding.apply {
                            email.isEnabled = false
                            password.isEnabled = false
                        }
                        login(email.text.toString(), password.text.toString())
                    }
                }
            }

        }


        binding.btnGoRegister.setOnClickListener {
            binding.loginMotion.transitionToStart()
            Handler().postDelayed({
                findNavController().navigate(R.id.action_logIn_to_signUp)
            },500)
        }
        return binding.root
    }
    private fun login(email:String,password:String){
        binding.loginMotion.transitionToStart()
        db.loginUser(email,password)
        db.isCorrect = { it,isDoctor ->
            if (it){
                Handler().postDelayed({
                    if (isDoctor){
                        startActivity(Intent(requireContext(), DoctorContainer::class.java))
                    }else{
                        startActivity(Intent(requireContext(), PatientContainer::class.java))
                    }
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(R.anim.come_left,R.anim.go_right)
                },200)
            }else{
                Toast.makeText(requireContext(),"Something Wrong!, Try Again",Toast.LENGTH_LONG).show()
                binding.password.setText("")
                binding.loginMotion.transitionToEnd()
            }
                binding.email.isEnabled = false
                binding.password.isEnabled = false

        }
    }
}