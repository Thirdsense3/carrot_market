package com.example.carrotmarket.dto

data class Board(
        var id : Int,
        var price: Long,
        var title: String,
        var text: String,
        var categoryId: Int,
        var location: String,
        var nickname: String,
        var registerDate: String,
        var deadlineDate: String,
        var dibsCnt: Int,
        var viewCnt: Int,
        var chatCnt: Int,
        var photo: String
)
