package com.example.carrotmarket

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerifyingEmailActivity : AppCompatActivity()
{
    private val TAG: String = "VerifyingEmailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifyingemail)
        var emailCode: String?= null

        if(intent.hasExtra("emailCode")){
            emailCode = intent.getStringExtra("emailCode").toString()
            Log.d(TAG,"랜덤 email 코드 값 : $emailCode ")
        }
        else{
            Log.d(TAG,"이메일 코드값 없음")
        }

        findViewById<Button>(R.id.verifybutton).setOnClickListener {
            val inputCode = findViewById<EditText>(R.id.verifyEditText).text.toString()
            if(inputCode == emailCode){
                Toast.makeText(this@VerifyingEmailActivity, "일치합니다.",Toast.LENGTH_SHORT).show()
                val nextIntent = Intent(this,LoginActivity::class.java)
                startActivity(nextIntent) // 화면 전환
            }
            else{
                Toast.makeText(this@VerifyingEmailActivity, "불일치합니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}