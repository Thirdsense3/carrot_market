package com.example.carrotmarket.network

import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.dto.CertificationCode
import com.example.carrotmarket.dto.Member
import okhttp3.MultipartBody
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
        @Field("locationX") locationX : Float,
        @Field("locationY") locationY : Float
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

    @Multipart
    @POST("board/picture")
    fun postPicture(
            @Part imageFile : MultipartBody.Part
    ) : Call<String>

    @POST("board/posting")
    @FormUrlEncoded
    fun boardPosting(
            @Field ("price") price : Long,
            @Field ("title") title : String,
            @Field ("text") text : String,
            @Field ("categoryId") categoryId : Int,
            @Field ("nickname") nickname : String,
            @Field ("registerDate") registerDate : String,
            @Field ("deadlineDate") deadLineDate : String,
            @Field ("locationX") locationX : Float,
            @Field ("locationY") locationY : Float,
            @Field("picture") picture : String
    ) : Call<Board>

    @GET("register/verifying/{email}")
    fun verifying(@Path("email") email: String): Call<CertificationCode>

    @GET("board/list")
    fun boardList() : Call<List<Board>>

    @GET("board/list/{id}")
    fun getBoard(@Path("id") id: Long?): Call<Board>
}
