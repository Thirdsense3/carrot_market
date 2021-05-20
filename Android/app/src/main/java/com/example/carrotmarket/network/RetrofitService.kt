package com.example.carrotmarket.network

import android.content.res.Resources
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.dto.CertificationCode
import com.example.carrotmarket.dto.Member
import okhttp3.MultipartBody
import okhttp3.ResponseBody
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
            @Part ("id") id : Long,
            @Part imageFile : ArrayList<MultipartBody.Part>
            //@Part imageFile : MultipartBody.Part
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

    @POST("board/delete")
    @FormUrlEncoded
    fun deleteBoard(@Field("id") id: Long) : Call<String>

    @GET("board/list/{id}")
    fun getBoard(@Path("id") id: Long?): Call<Board>

    @GET("download/{id}/preview")
    fun getPreviewImage(@Path("id") id: Long?): Call<ResponseBody>

    @GET("download/{id}/{filename}}")
    fun getBoardImage(@Path("id") id: Long?, picture: String?): Call<ResponseBody>

    @POST("/board/search")
    @FormUrlEncoded
    fun searchBoard(@Field("text") text: String?): Call<List<Board>>
}
