package com.example.palliativecare.notification

data class PushNotification (
    val data : NotificationsData,
    val to : String = ""
)