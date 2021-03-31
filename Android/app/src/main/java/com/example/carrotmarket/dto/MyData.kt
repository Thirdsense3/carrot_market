package com.example.carrotmarket.dto

object MyData {
    private lateinit var member : Member

    fun saveMyData(member: Member){
        MyData.member = member
    }

    fun getMember() : Member{
        return member
    }
}
