package com.example.carrotmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_test)

        val btPostBoard = findViewById<Button>(R.id.bt_menu_test1)
        val btDelete = findViewById<Button>(R.id.bt_menu_test2)
        val btList = findViewById<Button>(R.id.bt_menu_test3)

        val intentPostBoard = Intent(this, PostBoardActivity::class.java)
        val intentDelete = Intent(this, DeleteTestActivity::class.java)
        val intentList = Intent(this, ListActivity::class.java)

        btPostBoard.setOnClickListener {
            startActivity(intentPostBoard)
        }

        btDelete.setOnClickListener {
            startActivity(intentDelete)
        }

        btList.setOnClickListener {
            startActivity(intentList)
        }
    }
}