package com.example.palliativecare.ui.doctorScreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.palliativecare.R
import com.example.palliativecare.databinding.DoctorContainerBinding

class DoctorContainer : AppCompatActivity() {
    private lateinit var binding : DoctorContainerBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this,"Hi I am Doctor", Toast.LENGTH_LONG).show()
        binding.navigateIcon.setOnClickListener{
            binding.mainDrawer.open()
        }


        val nav = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = nav.navController
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.mainDrawer)
        binding.navigationView.setupWithNavController(navController)

    }
    override fun onSupportNavigateUp(): Boolean{
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}