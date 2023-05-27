package com.example.palliativecare.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Doctor(var name:String,var id:String,var token:String) :Parcelable