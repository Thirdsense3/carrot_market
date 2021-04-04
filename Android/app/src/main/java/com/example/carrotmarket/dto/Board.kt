package com.example.carrotmarket.dto

data class Board (
    var id : Long,
    var price : Long,
    var title : String,
    var text : String,
    var categoryId : Int,
    var locationX : Float,
    var locationY : Float,
    var nickname : String,
    var registerDate : String,
    var deadLineDate : String?,
    var dibsCnt : Int,
    var viewCnt : Int,
    var chatCnt : Int,
    var picture : String,
)
