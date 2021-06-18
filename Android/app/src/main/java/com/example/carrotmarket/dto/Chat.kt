package com.example.carrotmarket.dto

data class Chat (
    var id: Int,
    var nickname: String,
    var message: String,
    var unix: Long
)