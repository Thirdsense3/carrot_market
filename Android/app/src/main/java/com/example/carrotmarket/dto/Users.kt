package com.example.carrotmarket.dto

data class Users(
    var user_id: Long,
    var email: String,
    var password: String,
    var name: String,
    var nickname: String,
    var token: String
)