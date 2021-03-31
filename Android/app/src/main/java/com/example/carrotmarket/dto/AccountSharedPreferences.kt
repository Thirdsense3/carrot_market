package com.example.carrotmarket.dto

import android.content.Context
import android.content.SharedPreferences

object AccountSharedPreferences {
    private val MY_ACCOUNT : String = "account"
    private lateinit var prefs : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    //TODO change location
    fun setUserData(context : Context, input : Member) {
        prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        editor = prefs.edit()
        editor.putString("email", input.email)
        editor.putString("password", input.password)
        editor.putString("nickname", input.nickname)
        editor.putString("name", input.name)
        editor.putFloat("locationX", input.locationX)
        editor.putFloat("locationY", input.locationY)
        editor.commit()
    }

    fun getUserData(context: Context) : Member {
        prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val email = prefs.getString("email", "").toString()
        val password = prefs.getString("password", "").toString()
        val nickname = prefs.getString("nickname", "").toString()
        val name = prefs.getString("name", "").toString()
        val locationX = prefs.getFloat("locationX", 0F)
        val locationY = prefs.getFloat("locationY", 0F)
        val member = Member(email, password, name, locationX, locationY, nickname)

        return member
    }

    fun deleteUserData(context : Context) {
        prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}