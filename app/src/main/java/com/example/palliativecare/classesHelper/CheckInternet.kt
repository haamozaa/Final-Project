package com.example.palliativecare.classesHelper

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.palliativecare.R
import com.google.android.material.snackbar.Snackbar

class CheckInternet(var context:Context,var view:View){
    var onConnected : ((Boolean) -> Unit)? = null
    fun isConnected(){
        var connected: Boolean
        val cm = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
        if (connected){
            onConnected!!.invoke(true)
        }else{
            onConnected!!.invoke(false)
            Snackbar.make(view, "الانترنت مقطوع !", Snackbar.LENGTH_INDEFINITE).apply {
                setActionTextColor(context.getColor(R.color.main))
                duration = 1500
            }.show()

        }
    }
    fun isConnectedBool() : Boolean{
        var connected: Boolean
        val cm = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
        return connected
    }
}