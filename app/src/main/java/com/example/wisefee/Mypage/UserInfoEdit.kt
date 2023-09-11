package com.example.wisefee.Mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wisefee.Jwt_decoding
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.Login.MemberResponse
import com.example.wisefee.Login.UpdateMemberRequest
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.databinding.ActivityMyPageUserInfoEditBinding
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoEdit : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageUserInfoEditBinding
    private var updatedIsAuthEmail: String = ""
    private var updatedIsAllowPushMsg: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageUserInfoEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goBackButton.setOnClickListener { onBackPressed() }
        getUserInfo(this@UserInfoEdit)

        binding.userInfoEditSummit.setOnClickListener {
            // 사용자 정보를 수정하고 서버에 업데이트합니다.
            updateUserInfo(this@UserInfoEdit)
        }
}

    data class UserInfo(
        val nickname: String,
        val birth: String,
        val phone: String,
        val phoneOffice: String,
        val email: String
        // 다른 필드, 필요한 경우 추가
    )

    private fun displayUserInfo(userInfo: UserInfo) {
        binding.nickname.setText(userInfo.nickname)
        binding.birth.setText(userInfo.birth)
        binding.phone.setText(userInfo.phone)
        binding.phoneOffice.setText(userInfo.phoneOffice)
        binding.email.text = userInfo.email

        // 다른 UI 요소에 필요한 정보를 표시
    }

    fun getUserInfo(activity: Activity) {
        // id 추출하는 과정.
        val jwtToken: String? =
            getSharedPreferences("login_sp", Context.MODE_PRIVATE).getString("accessToken", null)
        if (jwtToken != null) {
            val decodedClaims = Jwt_decoding(jwtToken)
            if (decodedClaims != null) {
                val userId = decodedClaims.optInt("userId") // 사용자 ID
                if (userId != 0) { // 또는 다른 유효한 기본값
                    // userId를 사용하여 Retrofit 요청을 보냅니다.
                    (application as MasterApplication).service.getMemberById(userId)
                        .enqueue(object : Callback<MemberResponse> {
                            override fun onResponse(
                                call: Call<MemberResponse>,
                                response: Response<MemberResponse>
                            ) {
                                if (response.isSuccessful) {
                                    // 요청이 성공적으로 완료될 때의 처리
                                    val memberInfo = response.body()
                                    if (memberInfo != null) {
                                        // 사용자 정보에 접근할 수 있습니다.
                                        val userInfo = UserInfo(
                                            nickname = memberInfo.nickname ?: "",
                                            birth = memberInfo.birth ?: "",
                                            phone = memberInfo.phone ?: "",
                                            phoneOffice = memberInfo.phoneOffice ?: "",
                                            email = memberInfo.email ?: ""
                                        )
                                            updatedIsAuthEmail = memberInfo.isAuthEmail ?: ""
                                            updatedIsAllowPushMsg = memberInfo.isAllowPushMsg ?: ""
                                            // 다른 필드도 필요한 경우 추가

                                        // 화면에 기존 정보 표시
                                        displayUserInfo(userInfo)
                                    } else {
                                        // 응답 본문이 null인 경우 처리
                                    }
                                } else {
                                    val errorResponseBody = response.errorBody()?.string()
                                    if (errorResponseBody != null) {
                                        val errorJson = JSONObject(errorResponseBody)
                                        val errorMessage = errorJson.getString("message")

                                        // 에러 메시지 처리 (예: 토스트 메시지로 표시)
                                        Log.d("MyTag", errorMessage)
                                        Toast.makeText(
                                            activity,
                                            "해당 정보를 불러올 수 없습니다.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                                // 요청이 실패했을 때의 처리
                            }
                        })
                } else {
                    // userId가 유효하지 않은 경우 처리
                    Log.d("decode", "유효하지 않은 사용자 ID")
                }
            } else {
                // 디코딩 실패 시 처리
                Log.d("decode", "토큰 디코딩 실패")
            }
        } else {
            // jwtToken이 null인 경우 처리
            Log.d("decode", "jwtToken이 없습니다.")
        }
    }

    fun updateUserInfo(activity: Activity) {

        // 사용자가 입력한 정보를 가져옵니다.
        val updatedNickname = binding.nickname.text.toString()
        val updatedBirth = binding.birth.text.toString()
        val updatedPhone = binding.phone.text.toString()
        val updatedPhoneOffice = binding.phoneOffice.text.toString()
        // 그대로 값 전달

        // 수정된 정보를 UpdateMemberRequest 객체에 저장합니다.
        val updateMemberRequest = UpdateMemberRequest(
            nickname = updatedNickname,
            birth = updatedBirth,
            isAllowPushMsg = updatedIsAllowPushMsg,
            isAuthEmail = updatedIsAuthEmail,
            phone = updatedPhone,
            phoneOffice = updatedPhoneOffice
        )

        val jwtToken: String? =
            getSharedPreferences("login_sp", Context.MODE_PRIVATE).getString("accessToken", null)
        if (jwtToken != null) {
            val decodedClaims = Jwt_decoding(jwtToken)
            if (decodedClaims != null) {
                val userId = decodedClaims.optInt("userId") // 사용자 ID
                if (userId != 0) {
                    // userId를 사용하여 Retrofit 요청을 보냅니다.
                    (application as MasterApplication).service.updateMember(updateMemberRequest, userId)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                if (response.isSuccessful) {
                                    Log.d("MyTag", "성공")
                                    Toast.makeText(activity, "회원 정보가 수정되었습니다.", Toast.LENGTH_LONG).show()
                                    activity.startActivity(Intent(activity, MainActivity::class.java))
                                } else {
                                    val errorResponseBody = response.errorBody()?.string()
                                    if (errorResponseBody != null) {
                                        val errorJson = JSONObject(errorResponseBody)
                                        val errorsArray = errorJson.getJSONArray("errors")

                                        for (i in 0 until errorsArray.length()) {
                                            val errorObject = errorsArray.getJSONObject(i)
                                            val errorMessage = errorObject.getString("message")
                                            // 에러 메시지 처리 (예: 토스트 메시지로 표시)
                                            Log.d("MyTag", "This is a debug log message에러")
                                            Toast.makeText(
                                                activity,
                                                errorMessage,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                // 요청이 실패했을 때의 처리
                                // 에러 메시지를 표시하거나 다른 처리를 수행하세요.
                            }
                        })
                }
            }
        }
    }

}
