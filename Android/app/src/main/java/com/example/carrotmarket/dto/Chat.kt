package com.example.carrotmarket.dto

import java.sql.Timestamp

data class Chat (
    var id: Int,
    var nickname: String,
    var message: String,
    var time: Timestamp
)