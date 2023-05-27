package com.example.palliativecare.notification.api

import com.example.palliativecare.notification.PushNotification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type:application/json",
        "Authorization:key=AAAA3ACQzic:APA91bHPeii4yHqqsd4CmnAT8ZgQoJbmMmqNoLcEpf_0APUbwuPrSE9KEL4c7v_5eQIIcYz_twYQDtSOwqWtK4SBuQu1VCi591KPL3d99VeEAxQ5-ZE-rNRy0qPCcbb1NtrxTaV6xRYb")
    @POST("fcm/send")
    fun sendNotification(@Body notification: PushNotification) : Call<PushNotification>
}