package com.example.carrotmarket


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.carrotmarket.Network.RetrofitClient
import com.example.carrotmarket.Network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    lateinit var retrofit : Retrofit
    lateinit var myAPI : RetrofitService
    lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        var email: String = ""
        var pw: String = ""
        var ckpw = findViewById<EditText>(R.id.checkPasswordEditText)
        var textpw = findViewById<EditText>(R.id.checkpwTextView)
        var name = findViewById<EditText>(R.id.name).toString()
        var nn: String = ""
        var location = findViewById<EditText>(R.id.locationEditText).toString()

        val auth_event = findViewById<Button>(R.id.authButton)
        val nickname_event = findViewById<Button>(R.id.nickNameButton)
        val join_event = findViewById<Button>(R.id.joinButton)

        var pwCheck : Boolean = false
        var emailCheck : Boolean = false
        var nnCheck:Boolean =false

        val textView = findViewById<TextView>(R.id.textView)

        auth_event.setOnClickListener{
            val inputEmail = findViewById<EditText>(R.id.emailEditText).toString()
            val serverCheck = auth_ck(inputEmail)
            if(serverCheck){
                Toast.makeText(this@RegisterActivity, "사용가능한 이메일입니다.",Toast.LENGTH_SHORT).show()
                emailCheck = true;
                email = inputEmail
            }
            else{
                Toast.makeText(this@RegisterActivity, "중복된 이메일 입니다.",Toast.LENGTH_SHORT).show()
            }
        }

        nickname_event.setOnClickListener {
            val inputNickname = findViewById<EditText>(R.id.nickNameEditText).toString()
            val serverCheck = nickname_ck(inputNickname)
            if(serverCheck){
                Toast.makeText(this@RegisterActivity, "사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show()
                nnCheck = true
                nn = inputNickname
            }
            else{
                Toast.makeText(this@RegisterActivity, "중복된 닉네임입니다.",Toast.LENGTH_SHORT).show()
            }
        }

        join_event.setOnClickListener {
            if(pwCheck&&emailCheck&&nnCheck) {
                join(email, pw, name, nn, location)
                //TODO("서버에서 'mailSender'이용해서 다음 Activity에서 체크하기")
            }
            else{
                Toast.makeText(this@RegisterActivity, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            }
            //TODO ("'Member'에 저장된 애들 로그인 화면으로 넘겨주기")
        }

        ckpw.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pw1: String? = findViewById<EditText>(R.id.passwordEditText).toString()
                val pw2: String? = findViewById<EditText>(R.id.checkPasswordEditText).toString()

                if(pw1 == pw2 && pw1 != null){
                    pwCheck = true
                    textpw.setText("비밀번호 일치")
                }
                else{
                    pwCheck = false
                    textpw.setText("비밀번호 불일치")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    fun auth_ck(email:String): Boolean{
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)
        var emailCheck:String ?= null

        Runnable { myAPI.emailing(email).enqueue(object : Callback<Member>{
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG,t.message)
            }

            override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                response.body()?.let {
                    emailCheck = it.email
                } ?: Toast.makeText(this@RegisterActivity, "Body is null", Toast.LENGTH_SHORT).show()
            }
        })
        }.run()

        if(emailCheck == null){
            return true
        }

        return false
    }

    fun nickname_ck(nickname: String): Boolean{retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)
        var nicknameCheck:String ?= null

        Runnable { myAPI.nicknaming(nickname).enqueue(object : Callback<Member>{
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG,t.message)
            }

            override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                response.body()?.let {
                    nicknameCheck = it.email
                } ?: Toast.makeText(this@RegisterActivity, "Body is null", Toast.LENGTH_SHORT).show()
            }
        })
        }.run()

        if(nicknameCheck != null){
            return true
        }

        return false
    }

    fun join(email:String, pw:String, name: String, nickname: String, location: String){
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)

        Runnable { myAPI.signUp(email,pw,name,nickname,location).enqueue(object : Callback<Member>{
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG,t.message)
            }

            override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                response.body()?.let {
                    val memberResponse = Member(it.email,it.password,it.name,it.location,it.nickname)
                    member = memberResponse
                }
            }
        })
        }.run()
    }

}