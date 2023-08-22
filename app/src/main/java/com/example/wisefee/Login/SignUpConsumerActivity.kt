package com.example.wisefee.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wisefee.MasterApplication
import com.example.wisefee.RetrofitService
import com.example.wisefee.databinding.ActivitySignupConsumerBinding
import org.json.JSONObject
import org.mozilla.javascript.tools.jsc.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpConsumerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupConsumerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupConsumerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        register(this@SignUpConsumerActivity)
    }

    fun register(activity: Activity) {
        binding.signupConsumerSummit.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val nickname = binding.nickname.text.toString()
            val birth = binding.birth.text.toString()
            val phone = binding.phone.text.toString()
            val phoneOffice = binding.phoneOffice.text.toString()
            val accountType = "CONSUMER"
            val authType = "Google"
            val isAllowPushMsg = if (binding.isAllowPushMsg.isChecked) "TRUE" else "FALSE"
            val isAuthEmail = "TRUE"

            val signUpConsumerRequest = RetrofitService.SignUpConsumerRequest(
                email,
                password,
                nickname,
                accountType,
                authType,
                birth,
                isAllowPushMsg,
                isAuthEmail,
                phone,
                phoneOffice
            )

            (application as MasterApplication).service.register(
                signUpConsumerRequest
            ).enqueue(object : Callback<SignUpConsumerResponseOrError> {
                override fun onResponse(
                    call: Call<SignUpConsumerResponseOrError>,
                    response: Response<SignUpConsumerResponseOrError>
                ) {
                    if (response.isSuccessful) {
                        val signUpResponse: SignUpConsumerResponse? = response.body()
                            ?.let { it as? SignUpConsumerResponseOrError.Success }?.response
                        Log.d("MyTag", "This is a debug log message0")

                        if (signUpResponse != null) {
                            Log.d("MyTag", "This is a debug log message1")
                            (application as MasterApplication).createRetrofit()
                            Toast.makeText(activity, "가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Log.d("MyTag", "This is a debug log message2")
                        }

                    } else {
                        val errorResponseBody = response.errorBody()?.string()
                        if (errorResponseBody != null) {
                            val errorJson = JSONObject(errorResponseBody)
                            val errorMessage = errorJson.getString("message")
                            val errorsArray = errorJson.getJSONArray("errors")

                            for (i in 0 until errorsArray.length()) {
                                val error = errorsArray.getString(i)
                                // 에러 메시지 처리 (예: 토스트 메시지로 표시)
                                Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Log.d("MyTag", "This is a debug log message4")
                            Log.d("MyTag", "Error Response Body: ${response.errorBody()?.string()}")
                        }
                    }
                }

                override fun onFailure(
                    call: Call<SignUpConsumerResponseOrError>,
                    t: Throwable
                ) {
                    Log.e("MyTag", "Request failed: ${t.message}")
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

}
