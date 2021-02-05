package com.example.carrotmarket


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // 회원등록 레이아웃으로 교체

        var email = findViewById<EditText>(R.id.emailEditText).toString()
        var pw = findViewById<EditText>(R.id.passwordEditText).toString()
        var ckpw = findViewById<EditText>(R.id.checkPasswordEditText)
        var textpw = findViewById<EditText>(R.id.checkpwTextView)
        var name = findViewById<EditText>(R.id.name).toString()
        var nn = findViewById<EditText>(R.id.nickNameEditText).toString()
        var location = findViewById<EditText>(R.id.locationEditText).toString()

        val auth_event = findViewById<Button>(R.id.authButton)
        val nickname_event = findViewById<Button>(R.id.nickNameButton)
        val join_event = findViewById<Button>(R.id.joinButton)

        var pwCheck : Boolean = false;

        val textView = findViewById<TextView>(R.id.textView)

        auth_event.setOnClickListener{
            auth_ck()
        }

        nickname_event.setOnClickListener {
            nickname_ck()
        }

        join_event.setOnClickListener {
            join(email,pw,name,nn,location)
        }

        ckpw.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var pw1: String? = pw.toString()
                var pw2: String? = ckpw.toString()
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

    fun auth_ck(): Boolean{
        val email = findViewById<EditText>(R.id.emailEditText)
        //TODO "서버로 보내서 이메일 중복검사"

        return false
    }

    fun nickname_ck(): Boolean{
        val nickname = findViewById<EditText>(R.id.nickNameEditText)
        //TODO "서버로 보내서 닉네임 중복검사"

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
                Log.d(TAG,"Not yet implemented response : ${response.body()!!.email}")

                Log.d(TAG,"response : ${response.errorBody()}")
                Log.d(TAG,"response : ${response.message()}")
                Log.d(TAG,"response : ${response.code()}")
                Log.d(TAG,"response : ${response.raw().request().url().url()}")

            }
        })
        }.run()
    }

}