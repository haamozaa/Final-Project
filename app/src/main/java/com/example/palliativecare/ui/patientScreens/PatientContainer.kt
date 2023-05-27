package com.example.palliativecare.ui.patientScreens

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.palliativecare.R
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.PatientContainerBinding
import com.example.palliativecare.ui.patientScreens.searchScreens.SearchActivity
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.math.log

class PatientContainer : AppCompatActivity() {
    lateinit var binding : PatientContainerBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PatientContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigateIconP.setOnClickListener {
            binding.mainDrawer.open()
        }
        val nav = supportFragmentManager.findFragmentById(R.id.fragmentP) as NavHostFragment
        navController = nav.navController
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.mainDrawer)
        binding.navigationViewP.setupWithNavController(navController)
    }

}