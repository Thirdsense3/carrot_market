package com.example.carrotmarket

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.dto.Chat
import com.example.carrotmarket.dto.chat.ChatMessage
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.sql.Timestamp

class ChatRoomActivity: AppCompatActivity() {
    private val TAG = "ChatRoomActivity"
    private val retrofit = RetrofitClient.getInstance()
    private val myAPI: RetrofitService = retrofit.create(RetrofitService::class.java)
    private val currentTime: Timestamp = Timestamp(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatTest = initTestData()
        val userId = intent.extras!!.getInt("chatId")

        val recyvlerv = findViewById<RecyclerView>(R.id.recyvlerv)
        recyvlerv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyvlerv.adapter = ChatAdapter(chatTest)

        val sendBtn = findViewById<ImageButton>(R.id.btn_send1)
        val sendMsg:EditText = findViewById<EditText>(R.id.sendText)

        sendBtn.setOnClickListener(){
            if(sendMsg.text.toString().isNotEmpty()){
                val msg:ChatMessage = makeMessage(userId,sendMsg.text.toString(),0);

                myAPI.sendMessage(msg.userId,msg.message,msg.time,msg.roomId).enqueue(object : Callback<String>{
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("connect FAIL", t.message);
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.isSuccessful){
                            Toast.makeText(this@ChatRoomActivity,"send Message",Toast.LENGTH_LONG).show()
                            Log.d(TAG,"send Message")
                        }
                        else{
                            Log.d(TAG,"fail sending")
                        }
                    }
                })

                /**
                 * 자기 자신인 경우 id = 1, 상대방인 경우 id=2로 설정
                 * */

                chatTest.add(Chat(1,"test1",sendMsg.text.toString(),currentTime))
                (recyvlerv.adapter as ChatAdapter).notifyItemInserted(0)            }
        }


    }

    private fun initTestData(): MutableList<Chat> {
        return mutableListOf<Chat>(
            Chat(2, "human2", "테스트입니다. 1", currentTime),
            Chat(2, "human2", "테스트입니다. 2", currentTime),
            Chat(2, "human2", "테스트입니다. 3", currentTime),
            Chat(1, "human1", "테스트입니다. 4", currentTime),
            Chat(2, "human2", "테스트입니다. 5", currentTime)
        )
    }

    private fun makeMessage(userId: Int,Message: String,roomId: Int): ChatMessage {
        val time: Timestamp = Timestamp(System.currentTimeMillis())

        return ChatMessage(userId, Message, time, roomId)
    }
}