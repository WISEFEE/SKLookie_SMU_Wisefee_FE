package com.example.wisefee.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.RetrofitService
import com.example.wisefee.Store.StoreActivity
import com.example.wisefee.databinding.ActivityLoginBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class   LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        binding.signup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }
        setupListener(this@LoginActivity)
    }


    fun setupListener(activity: Activity) {
        binding.login.setOnClickListener {

            val email = binding.idInputbox.text.toString()
            val fcmToken = getSharedPreferences("token", Context.MODE_PRIVATE).getString("token", null) as String
            val password = binding.passwordInputbox.text.toString()

            val accountType = when {
                binding.accountTypeConsumer.isChecked -> "consumer"
                binding.accountTypeSeller.isChecked -> "seller"
                else -> "consumer"
            }

            Log.d("tag", "$fcmToken")
            val loginRequest = LoginRequest(email, fcmToken, password)

            (application as MasterApplication).service.login(
                loginRequest
            ).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        val accessToken = user!!.accessToken!!

                        saveUserToken(accessToken, activity)
                        (application as MasterApplication).createRetrofit()
                        Toast.makeText(activity, "로그인 하셨습니다.", Toast.LENGTH_LONG).show()

                        if (accountType == "consumer") {
                            startActivity(Intent(activity, MainActivity::class.java))
                        } else {
                            startActivity(Intent(activity, StoreActivity::class.java))
                        }


                    } else {
                        val errorResponseBody = response.errorBody()?.string()
                        if (errorResponseBody != null) {
                            val errorJson = JSONObject(errorResponseBody)
                            val errorMessage = errorJson.getString("message")

                            // 에러 메시지 처리 (예: 토스트 메시지로 표시)
                            Log.d("MyTag", errorMessage)
                            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun saveUserToken(accessToken: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("accessToken", accessToken)
        editor.commit()
    }

}