package com.example.carrotmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.carrotmarket.dto.Board
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DeleteTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_test)

        var retrofit = RetrofitClient.getInstance()
        var myApi = retrofit.create(RetrofitService::class.java)

        val btDelete = findViewById<Button>(R.id.deleteBt)
//        val board : Board = Board(1,0,"","",0,0F,0F,"", "", "", 0, 0, 0, "null")
        val id : Long = 33

        btDelete.setOnClickListener {
            myApi.deleteBoard(id).enqueue(object : Callback<Board> {
                override fun onResponse(call: Call<Board>, response: Response<Board>) {
                    if (response?.isSuccessful) {
                        Log.d("DeleteTestActivity", response.toString())
                        Log.d("DeleteTestActivity", call.toString())
                    } else {
                        Log.d("DeleteTestActivity", "fail")
                    }
                }

                override fun onFailure(call: Call<Board>, t: Throwable) {
                    Log.d("FILE : ", call.toString())
                    Log.d("FAIL", t.message)
                }
            })
        }
    }
}