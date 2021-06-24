package com.example.carrotmarket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.dto.Chat

class ChatRoomActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatTest = initTestData()

        val recyvlerv = findViewById<RecyclerView>(R.id.recyvlerv)
        recyvlerv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyvlerv.adapter = ChatAdapter(chatTest)
    }

    fun initTestData(): MutableList<Chat> {
        return mutableListOf<Chat>(Chat(2, "human2", "테스트입니다. 1", 1624004430),
            Chat(2, "human2", "테스트입니다. 2", 1624004450),
            Chat(2, "human2", "테스트입니다. 3", 1624004500),
            Chat(1, "human1", "테스트입니다. 4", 1624004536),
            Chat(2, "human2", "테스트입니다. 5", 1624004600))
    }
}