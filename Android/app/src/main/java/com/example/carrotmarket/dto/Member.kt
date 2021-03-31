package com.example.carrotmarket.dto

data class Member(
        var email: String,
        var password: String,
        var name: String,
        var locationX: Float,
        var locationY: Float,
        var nickname: String
)