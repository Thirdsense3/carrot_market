package com.example.carrotmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

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

            val params = HashMap<String, String> ()
            params["Email"] = email
            params["password"] = password
            val jsonObject = JSONObject(params as Map<*, *>)

            val que = Volley.newRequestQueue(this)

            // checking response
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                        try {
                            textview.text = "Response: $response"
                        } catch (e:Exception) {
                            textview.text = "Exception: $e"
                        }
                    }, {
                textview.text = "Volley error : $it"
            })
            que.add(request)
        }
    }
}