package com.example.carrotmarket.dto.chat

import com.example.carrotmarket.dto.chat.User

class BaseMessage {
    var message: String? = null
    var sender: User? = null
    var createdAt: Long = 0
}