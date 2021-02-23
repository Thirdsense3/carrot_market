package com.example.carrotmarket


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.carrotmarket.dto.CertificationCode
import com.example.carrotmarket.dto.Member
import com.example.carrotmarket.network.RetrofitClient
import com.example.carrotmarket.network.RetrofitService
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var member: Member
    lateinit var certificationcode: CertificationCode

    sealed class Result<out R>{
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        var email: String = ""
        var ckpw = findViewById<EditText>(R.id.checkPasswordEditText)
        var textpw = findViewById<EditText>(R.id.checkpwTextView)
        var nn: String = ""

        val authEvent = findViewById<Button>(R.id.authButton)
        val nicknameEvent = findViewById<Button>(R.id.nickNameButton)
        val joinEvent = findViewById<Button>(R.id.joinButton)

        var pwCheck: Boolean = false
        var emailCheck: Boolean = false
        var nnCheck: Boolean = false


        authEvent.setOnClickListener {

            val inputEmail = findViewById<EditText>(R.id.emailEditText).text.toString()

            Log.v(TAG, "email : $inputEmail")

            val serverCheck = authCk(inputEmail)
            if (serverCheck) {
                Toast.makeText(this@RegisterActivity, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                emailCheck = true;
                email = inputEmail
            } else {
                Toast.makeText(this@RegisterActivity, "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        nicknameEvent.setOnClickListener {
            val inputNickname = findViewById<EditText>(R.id.nickNameEditText).text.toString()
            Log.v(TAG, "nickname : $inputNickname")

            val serverCheck = nicknameCk(inputNickname)

            if (serverCheck) {
                Toast.makeText(this@RegisterActivity, "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                nnCheck = true
                nn = inputNickname
            } else {
                Log.d(TAG, serverCheck.toString())
                Toast.makeText(this@RegisterActivity, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        joinEvent.setOnClickListener {
            Log.d(TAG, "$pwCheck $emailCheck $nnCheck")
            if (pwCheck && emailCheck && nnCheck) {
                val pw = findViewById<EditText>(R.id.passwordEditText).text.toString()
                val name = findViewById<EditText>(R.id.nameEditText).text.toString()
                val location = findViewById<EditText>(R.id.locationEditText).text.toString()

                register(email, pw, name, nn, location)
            } else {
                Toast.makeText(this@RegisterActivity, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            }
            //TODO ("'Member'에 저장된 애들 로그인 화면으로 넘겨주기")
        }

        ckpw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pw1: String = findViewById<EditText>(R.id.passwordEditText).text.toString()
                val pw2: String = findViewById<EditText>(R.id.checkPasswordEditText).text.toString()

                if (pw1 == pw2 && pw1.isNotEmpty()) {
                    pwCheck = true
                    textpw.setText("비밀번호 일치")
                } else {
                    pwCheck = false
                    textpw.setText("비밀번호 불일치")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    class SyncViewModel(): ViewModel(){

    }

    private fun authCk(email: String): Boolean {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)
        var emailCheck: String? = null

        myAPI.emailing(email).enqueue(object : Callback<Member> {
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                response.body()?.let {
                    emailCheck = it.email
                }
                        ?: Toast.makeText(this@RegisterActivity, "Body is null", Toast.LENGTH_SHORT).show()
            }
        })

        Log.v(TAG, "이메일 확인 : $emailCheck")

        if (emailCheck == null) {
            return true
        }

        return false
    }

    private fun nicknameCk(nickname: String): Boolean {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)
        var nicknameCheck: String? = null

        myAPI.nicknaming(nickname).enqueue(object : Callback<Member> {
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                response.body()?.let {
                    nicknameCheck = it.email
                }
                        ?: Toast.makeText(this@RegisterActivity, "Body is null", Toast.LENGTH_SHORT).show()
            }
        })

        Log.v(TAG, "닉네임 확인 : $nicknameCheck")

        if (nicknameCheck != null) {
            return false
        }

        return true
    }

    private suspend fun join(email: String, pw: String, name: String, nickname: String, location: String) {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(RetrofitService::class.java)

        Log.v(TAG, "join 실행")

        myAPI.signUp(email, pw, name, nickname, location).enqueue(object : Callback<Member> {
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<Member>, response: retrofit2.Response<Member>) {
                response.body()?.let {
                    member = Member(it.email, it.password, it.name, it.location, it.nickname)
                }
            }
        })

        Log.v(TAG, "join 끝남")
    }

    private suspend fun verifying(email: String) {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create((RetrofitService::class.java))
        Log.v(TAG, "verifying 실행 인증메일 보냄")

        CoroutineScope(IO).launch{
            myAPI.verifying(email).enqueue(object : Callback<CertificationCode> {
                override fun onFailure(call: Call<CertificationCode>, t: Throwable) {
                    Log.d(TAG, t.message)
                    Log.v(TAG, "연결 실패")
                }

                override fun onResponse(call: Call<CertificationCode>, response: retrofit2.Response<CertificationCode>) {
                    response.body()?.let {
                        certificationcode = CertificationCode(it.code)
                        Log.v(TAG, "인증메일 받음 : ${certificationcode.code}")
                    }?: Toast.makeText(this@RegisterActivity, "Body is null", Toast.LENGTH_SHORT).show()

                }
            })

            Log.v(TAG, "verifying 끝남")
        }
    }

    private suspend fun checkAndRegister(){
        val nextIntent = Intent(this@RegisterActivity, VerifyingEmailActivity::class.java)

        if (this@RegisterActivity::certificationcode.isInitialized) {
            Log.v(TAG, "emailCode : ${certificationcode.code}")
            nextIntent.putExtra("emailCode", certificationcode.code)
            startActivity(nextIntent) // 화면 전환
        } else {
            Log.v(TAG, "초기화 안됨")
            Toast.makeText(this@RegisterActivity, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun register(email: String, pw: String, name: String, nickname: String, location: String) {
        /**
         * Coroutine 이용해서 초기화후에 다음코드 돌아가게 작성
         * */

        CoroutineScope(IO).launch {
            join(email, pw, name, nickname, location)
            verifying(email)

            val job = CoroutineScope(Main).launch {
                checkAndRegister()
            }
            job.join()
        }
    }

}

