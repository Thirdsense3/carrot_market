package com.example.carrotmarket

import java.io.Serializable

data class Member(
        var email : String,
        var password : String,
        var name : String,
        var nickname : String,
        var location : String
) : Serializable