package com.example.carrotmarket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity: AppCompatActivity() {
    private val TAG = "BoardActivity"
    lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)
        val url = "http://10.0.2.2:8080/login"
        val retrofit = RetrofitClient.getInstance()
        val myAPI = retrofit.create(RetrofitService::class.java)


        CoroutineScope(IO).launch {
            myAPI.boardList().enqueue(object : Callback<Board>{
                override fun onFailure(call: Call<Board>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    response.body()?.let {
                        board = Board(it.id, it.price,it.title,it.text,it.categoryId,it.location,it.nickname,it.registerDate,it.deadlineDate,it.dibsCnt,it.viewCnt,it.chatCnt)
                    }
                }
            })

        }
    }

}