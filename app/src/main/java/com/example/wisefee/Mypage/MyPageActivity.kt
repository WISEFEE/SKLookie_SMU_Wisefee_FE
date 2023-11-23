package com.example.wisefee.Mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.jwtDecoding
import com.example.wisefee.Login.MemberResponse
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.Subscribe.MySubscriptionHomeActivity
import com.example.wisefee.Subscribe.MySubscriptionNoActivity
import com.example.wisefee.databinding.ActivityMyPageBinding
import com.example.wisefee.dto.MyPageResponseDTO
import com.example.wisefee.dto.SubscribeResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMyPageBinding
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        bindingName()
    }

    private fun initialize() {
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.myAppSettings.setOnClickListener { startActivity(Intent(this@MyPageActivity, AppSettings::class.java)) }

        binding.mySubscriptions.setOnClickListener {
            masterApplication.service.getSubscribeInfo().enqueue(object : Callback<SubscribeResponseDTO> {
                override fun onResponse(
                    call: Call<SubscribeResponseDTO>,
                    response: Response<SubscribeResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        val subscribeInfo = response.body()?.cafes
                        if (subscribeInfo.isNullOrEmpty()) {
                            // 구독 안했을때
                            startActivity(Intent(this@MyPageActivity, MySubscriptionNoActivity::class.java))
                        } else {
                            // 구독 했을때
                            startActivity(Intent(this@MyPageActivity, MySubscriptionHomeActivity::class.java))
                        }
                    }
                }
                override fun onFailure(call: Call<SubscribeResponseDTO>, t: Throwable) {
                    Log.d("SearchingStores", "failed loading cafe image", t)
                }
            })
        }

        binding.myPaymentHistory.setOnClickListener { startActivity(Intent(this@MyPageActivity, PaymentHistory::class.java)) }
        binding.customerInquiry.setOnClickListener { startActivity(Intent(this@MyPageActivity, CustomerInquiry::class.java)) }
        binding.userInfoEditBtn1.setOnClickListener { startActivity(Intent(this@MyPageActivity, UserInfoEdit::class.java)) }
        binding.userInfoEditBtn2.setOnClickListener { startActivity(Intent(this@MyPageActivity, UserInfoEdit::class.java)) }

        binding.userInfoSignOutBtn1.setOnClickListener { startActivity(Intent(this@MyPageActivity, LoginActivity::class.java))
            getSharedPreferences("login_sp", Context.MODE_PRIVATE).edit().remove("accessToken").apply()
            Toast.makeText(this@MyPageActivity, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
        }
        binding.userInfoSignOutBtn2.setOnClickListener { startActivity(Intent(this@MyPageActivity, LoginActivity::class.java))
            getSharedPreferences("login_sp", Context.MODE_PRIVATE).edit().remove("accessToken").apply()
            Toast.makeText(this@MyPageActivity, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
        }



        binding.home.setOnClickListener {
            startActivity(Intent(this@MyPageActivity, MainActivity::class.java))
            finish()
        }
        binding.rental.setOnClickListener {
            startActivity(Intent(this@MyPageActivity, SearchingStoresActivity::class.java))
            finish()
        }
        binding.returnTumbler.setOnClickListener {
            startActivity(Intent(this@MyPageActivity, ReturnTumblerActivity::class.java))
            finish()
        }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)
    }

    private fun bindingName() {
        val jwtToken = masterApplication.getUserToken()

        if (jwtToken == null) {
            Log.d("decode", "JWT token is null")
            return
        }

        // jwt decoding
        val decodeClaims = jwtDecoding(jwtToken)
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
