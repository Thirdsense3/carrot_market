package com.example.carrotmarket.network

import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.dto.CertificationCode
import com.example.carrotmarket.dto.Member
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("register/member")
    @FormUrlEncoded
    fun signUp(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("name") name:String,
        @Field("nickname") nickname: String,
        @Field("location") location : String
    ): Call<Member>

    @GET("register/emailing/{id}")
    fun emailing(@Path("id") id: String): Call<Member>

    @GET("register/nicknaming/{nickname}")
    fun nicknaming(@Path("nickname") nickname: String): Call<Member>

    @POST("login")
    @FormUrlEncoded
    fun login(
            @Field("email") email : String,
            @Field("password") password : String
    ) : Call<Member>

    @GET("register/verifying/{email}")
    fun verifying(@Path("email") email: String): Call<CertificationCode>

    @GET("board/list")
    fun boardList() : Call<Board>
}
