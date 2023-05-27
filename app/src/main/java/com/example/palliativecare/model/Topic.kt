package com.example.palliativecare.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(var doctorId:String? = null,var topicTitle:String? = null,var topic:String? = null,var topicImage:String? = null,var topicPdf:String? = null,var topicId:String? = null,var categoryId:String? = null) : Parcelable
