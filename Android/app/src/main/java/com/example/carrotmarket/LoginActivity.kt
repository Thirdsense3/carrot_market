package com.example.carrotmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.carrotmarket.dto.Member
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val url = "http://10.0.2.2:8080/login"
        val emailEditText = findViewById<EditText>(R.id.emailEditText);
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText);
        val joinButton = findViewById<Button>(R.id.joinButton);
        val loginButton = findViewById<Button>(R.id.loginButton);
        // testing response
        val textview = findViewById<TextView>(R.id.textview)


        loginButton.setOnClickListener() {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val retrofit = RetrofitClient.getInstance()
            val myApi = retrofit.create(RetrofitService::class.java)

            Runnable {
                myApi.login(email, password).enqueue(object : Callback<Member> {
                    override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                        response.body()?.let {
                            if(!it.email.toString().equals("error")) {
                                val member = Member(it.email, it.password, it.name, it.location, it.nickname)
                                textview.text = member.email + " " + member.password
                                //intent.putExtra("member", member)
                                //startActivity(intent)
                            }
                            else {
                                textview.text = "아이디 또는 비밀번호 오류입니다."
                            }
                        }
                    }

                    override fun onFailure(call: Call<Member>, t: Throwable) {
                        textview.text = t.message
                    }
                })
            }.run()
        }

        joinButton.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}