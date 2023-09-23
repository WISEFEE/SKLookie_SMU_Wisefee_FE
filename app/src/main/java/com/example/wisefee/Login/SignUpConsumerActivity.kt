package com.example.wisefee.Login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wisefee.MasterApplication
import com.example.wisefee.RetrofitService
import com.example.wisefee.databinding.ActivitySignupConsumerBinding
import okhttp3.ResponseBody
import org.json.JSONObject
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

            val signUpConsumerRequest = SignUpConsumerRequest(
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
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("MyTag", "성공")
                            Toast.makeText(activity, "가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                            activity.startActivity(Intent(activity, LoginActivity::class.java))
                    }

                    else {
                        val errorResponseBody = response.errorBody()?.string()
                        if (errorResponseBody != null) {
                            val errorJson = JSONObject(errorResponseBody)
                            val errorsArray = errorJson.getJSONArray("errors")

                            for (i in 0 until errorsArray.length()) {
                                val errorObject = errorsArray.getJSONObject(i)
                                val errorMessage = errorObject.getString("message")
                                // 에러 메시지 처리 (예: 토스트 메시지로 표시)
                                Log.d("MyTag", "This is a debug log message에러")
                                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Log.d("MyTag", "This is a debug log message4")
                            Log.d("MyTag", "Error Response Body: ${response.errorBody()?.string()}")
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBody>,
                    t: Throwable
                ) {
                    Log.e("MyTag", "Request failed: ${t.message}")
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

}
