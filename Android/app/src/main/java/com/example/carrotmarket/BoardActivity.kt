package com.example.carrotmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.carrotmarket.dto.AccountSharedPreferences
import com.example.carrotmarket.dto.MyData

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        val temp = findViewById<TextView>(R.id.temp)
        val logoutButton = findViewById<Button>(R.id.logout)
        val postButton = findViewById<Button>(R.id.post)

        var member = MyData.getMember()
        temp.setText(member.email)

        logoutButton.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            AccountSharedPreferences.deleteUserData(this)
            startActivity(intent)
        }

        postButton.setOnClickListener() {
            val intent = Intent(this, PostBoardActivity::class.java)
            startActivity(intent)
        }
    }
}