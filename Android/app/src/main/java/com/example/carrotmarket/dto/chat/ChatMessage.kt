package com.example.carrotmarket.dto.chat

import java.sql.Timestamp

data class ChatMessage (
    var userId: Int,
    var message:String,
    var time:Timestamp,
    var roomId: Int
)