package com.example.carrotmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.carrotmarket.dto.AccountSharedPreferences
import com.example.carrotmarket.dto.Member
import com.example.carrotmarket.dto.MyData
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {

    var backPressedTime : Long = 0;

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
        val autoLogin = findViewById<CheckBox>(R.id.autoLogin)
        val intent = Intent(this, ListActivity::class.java)


        val check = AccountSharedPreferences.getUserData(this)
        if(check.email != "") {
            MyData.saveMyData(check)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

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
                                val member = Member(it.email, it.password, it.name, it.locationX, it.locationY, it.nickname)
                                textview.text = member.email + " " + member.password

                                MyData.saveMyData(member)

                                if(autoLogin.isChecked())
                                    AccountSharedPreferences.setUserData(this@LoginActivity, member)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
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

    override fun onBackPressed() {
        // super.onBackPressed()
        var tempTime : Long = System.currentTimeMillis()
        var intervalTime : Long = tempTime - backPressedTime
        val toast = Toast.makeText(this@LoginActivity,"한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)

        if(System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            toast.show()
            return
        }

        if(System.currentTimeMillis() <= backPressedTime + 2000) {
            finishAffinity()
            toast.cancel()
            System.runFinalization()
            System.exit(0)
        }
    }
}