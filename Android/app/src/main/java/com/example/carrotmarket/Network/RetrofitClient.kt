package com.example.carrotmarket.Network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://localhost:8080/"
    private var instance : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance() : Retrofit{
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }
}