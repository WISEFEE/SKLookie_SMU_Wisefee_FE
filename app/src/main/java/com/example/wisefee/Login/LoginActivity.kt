package com.example.wisefee.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityLoginBinding
import org.mozilla.javascript.tools.jsc.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.loginKakao.setOnClickListener { startActivity(Intent(this, _::class.java)) }
//        binding.loginNaver.setOnClickListener { startActivity(Intent(this, _::class.java)) }
//        binding.loginGoogle.setOnClickListener { startActivity(Intent(this, _::class.java)) }
        binding.login.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        binding.signup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }

        setupListener(this@LoginActivity)
    }


    fun setupListener(activity: Activity) {

        binding.login.setOnClickListener {
            val email = binding.idInputbox.text.toString()
            val password = binding.passwordInputbox.text.toString()

            (application as MasterApplication).service.login(
                email, password
            ).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()

                        val token = user!!.accessToken!!
                        saveUserToken(email, token, activity)
                        (application as MasterApplication).createRetrofit()

                        Toast.makeText(activity, "로그인 하셨습니다.", Toast.LENGTH_LONG).show()
                        startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        Toast.makeText(activity, "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun saveUserToken(username: String, token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("token", token)
        editor.apply()
    }

}