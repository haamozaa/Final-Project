package com.example.palliativecare.model

class Message {
    var messageId : String? = null
    var message : String? = null
    var senderId : String? = null
    var imageUrl : String? = null
    var timeStamp : Long? = null
    constructor(){}
    constructor(
        message: String?,
        senderId: String?,
        timeStamp: Long?
    ){
        this.message = message
        this.senderId = senderId
        this.timeStamp = timeStamp
    }
}