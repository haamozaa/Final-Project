package com.example.palliativecare.model

import com.google.firebase.firestore.DocumentId

data class User(var fName:String? = null, var sName:String? = null, var lName:String? = null, var email:String? = null, var phoneNumber:String? = null, var dOB:String? = null, var address:String? = null,var category:String? = null)