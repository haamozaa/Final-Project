package com.example.palliativecare.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.palliativecare.R
import com.example.palliativecare.Splash
import com.example.palliativecare.ui.registration.RegistrationContainer
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MYFirebaseMessagingService : FirebaseMessagingService() {
    private val channelId = "palliative"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
                val intent = Intent(this,Splash::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                createNotificationChannel(manager)
                val intent1 = PendingIntent.getActivities(this,0, arrayOf(intent),PendingIntent.FLAG_IMMUTABLE)
                val notification = NotificationCompat.Builder(this,channelId)
                    .setContentTitle(message.data["title"])
                    .setContentText(message.data["message"])
                    .setSmallIcon(R.drawable.chat_icon)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build()
                manager.notify(Random.nextInt(),notification)
    }
    private fun createNotificationChannel(manager:NotificationManager){
        val channel = NotificationChannel(channelId,"palliativeChat",NotificationManager.IMPORTANCE_HIGH)
        channel.description = "New Chat"
        channel.enableLights(true)
        manager.createNotificationChannel(channel)
    }
}