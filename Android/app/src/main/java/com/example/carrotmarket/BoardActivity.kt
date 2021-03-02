package com.example.carrotmarket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BoardActivity: AppCompatActivity() {
    private val TAG = "BoardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardlist)
    }
}