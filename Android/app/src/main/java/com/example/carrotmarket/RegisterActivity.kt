package com.example.carrotmarket


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // 회원등록 레이아웃으로 교체

        var email = findViewById<EditText>(R.id.emailEditText)
        var pw = findViewById<EditText>(R.id.passwordEditText)
        var ckpw = findViewById<EditText>(R.id.checkPasswordEditText)
        var nn = findViewById<EditText>(R.id.nickNameEditText)
        var location = findViewById<EditText>(R.id.locationEditText)

        val auth_event = findViewById<Button>(R.id.authButton)
        val nickname_event = findViewById<Button>(R.id.nickNameButton)
        val join_event = findViewById<Button>(R.id.joinButton)

        var pwCheck : Boolean = false;

        auth_event.setOnClickListener{
            auth_ck()
        }

        nickname_event.setOnClickListener {
            nickname_ck()
        }

        join_event.setOnClickListener {
            join()
        }

        ckpw.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(pw.toString() == ckpw.toString() && pw.toString() != null){
                    pwCheck = true;
                    //TODO "TRUE 결과 출력할 text view 설정"
                }
                else{
                    pwCheck = false;
                    //TODO "FALSE 결과 출력할 text view 설정"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun auth_ck(){
        val email = findViewById<EditText>(R.id.emailEditText)
        //TODO "서버로 보내서 이메일 중복검사"
    }

    fun nickname_ck(){
        val nickname = findViewById<EditText>(R.id.nickNameEditText)
        //TODO "서버로 보내서 닉네임 중복검사"
    }

    fun join(){
        //TODO "전부 서버로 보냄"
    }
    


}