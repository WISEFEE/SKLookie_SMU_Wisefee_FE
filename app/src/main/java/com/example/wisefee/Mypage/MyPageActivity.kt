package com.example.wisefee.Mypage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.Jwt_decoding
import com.example.wisefee.Login.MemberResponse
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStores
import com.example.wisefee.databinding.ActivityMyPageBinding
import com.example.wisefee.dto.MyPageResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMyPageBinding
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        // init UI
        initializeUI()

        // binding nick_name
        bindingName()
    }

    private fun initializeUI() {
        binding.apply {
            myAppSettings.setOnClickListener { startActivity(Intent(this@MyPageActivity, AppSettings::class.java)) }
            mySubscriptions.setOnClickListener { startActivity(Intent(this@MyPageActivity, MySubscription_home::class.java)) }
            myPaymentHistory.setOnClickListener { startActivity(Intent(this@MyPageActivity, PaymentHistory::class.java)) }
            customerInquiry.setOnClickListener { startActivity(Intent(this@MyPageActivity, CustomerInquiry::class.java)) }
            userInfoEditBtn1.setOnClickListener { startActivity(Intent(this@MyPageActivity, UserInfoEdit::class.java)) }
            userInfoEditBtn2.setOnClickListener { startActivity(Intent(this@MyPageActivity, UserInfoEdit::class.java)) }

            home.setOnClickListener {
                startActivity(Intent(this@MyPageActivity, MainActivity::class.java))
                finish()
            }
            rental.setOnClickListener {
                startActivity(Intent(this@MyPageActivity, SearchingStores::class.java))
                finish()
            }
            returnTumbler.setOnClickListener {
                startActivity(Intent(this@MyPageActivity, ReturnTumblerActivity::class.java))
                finish()
            }
            mypage.setImageResource(R.drawable.clicked_mypage)
        }
    }

    private fun bindingName() {
        val jwtToken = masterApplication.getUserToken()

        if (jwtToken == null) {
            Log.d("decode", "JWT token is null")
            return
        }

        // jwt decoding
        val decodeClaims = Jwt_decoding(jwtToken)
        if (decodeClaims == null) {
            Log.d("decode", "Failed decoding JWT token")
            return
        }

        // get userId from jwt
        val userId = decodeClaims.optInt("userId")
        if (userId == 0) {
            Log.d("decode", "Invalid user id")
            return
        }

        fetchMemberInfo(userId)
    }

    private fun fetchMemberInfo(userId: Int) {
        masterApplication.service.getMemberById(userId).enqueue(object : Callback<MemberResponse> {
            override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { memberInfo ->
                        // update userInfo
                        val userInfo = MyPageResponseDTO(
                            nickname = memberInfo.nickname ?: " "
                        )
                        // binding name
                        displayUserInfo(userInfo)
                    }
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("fetchMemberInfo", "Failed to fetch member info", t)
            }
        })
    }

    private fun displayUserInfo(userInfo: MyPageResponseDTO) {
        binding.nickName.text = userInfo.nickname
    }

    override fun onPause() {
        super.onPause()
    }
}
