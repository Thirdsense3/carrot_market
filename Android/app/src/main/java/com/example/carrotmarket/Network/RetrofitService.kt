package com.example.carrotmarket.Network

import com.example.carrotmarket.Member
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("list")
    fun getAuth(@Query("id") id : String) : Call<Member>

    @POST("register")
    @FormUrlEncoded
    fun signUp(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("name") name:String,
        @Field("nickname") nickname: String,
        @Field("location") location : String
    ): Call<Member>
}
