package com.example.wisefee.Subscribe

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.databinding.ActivityMySubscriptionHomeBinding
import com.example.wisefee.dto.SubCafe
import com.example.wisefee.dto.SubscribeHistoryDTO
import com.example.wisefee.dto.SubscribeResponseDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySubscriptionHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMySubscriptionHomeBinding
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        bindingImage()
    }

    // TODO 사용자는 구독권 1개만 가질 수 있다고 가정하고 진행함.
    private fun bindingImage(){
        val cafeView = layoutInflater.inflate(R.layout.activity_my_subscription_home , null)
        masterApplication.service.getSubscribeInfo().enqueue(object : Callback<SubscribeResponseDTO> {
            override fun onResponse(
                call: Call<SubscribeResponseDTO>,
                response: Response<SubscribeResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val subscribeInfo = response.body()?.cafes
                    if (subscribeInfo != null) {
                        cafeView.findViewById<TextView>(R.id.sub_cafe_name).text = subscribeInfo[0].title + " 구독권"
                        bindingCafeImg(subscribeInfo[0], cafeView)
                    }
                }
            }
            override fun onFailure(call: Call<SubscribeResponseDTO>, t: Throwable) {
                Log.d("SearchingStores", "failed loading cafe image", t)
            }
        })

        getCafeHistoryInfo(cafeView)

        // TODO 여기에 2단계, 3단계 구독권 ListUp하기!!
        // /api/v1/subTicketType 써서 넣기.

        setContentView(cafeView)
    }
    private fun getCafeHistoryInfo(cafeView: View) {
        masterApplication.service.getSubscribeHistory().enqueue(object :
            Callback<SubscribeHistoryDTO> {
            override fun onResponse(
                call: Call<SubscribeHistoryDTO>,
                response: Response<SubscribeHistoryDTO>
            ) {
                if (response.isSuccessful) {
                    val subCafe = response.body()?.subscribes?.get(0)
                    Log.d("Aaaaa", subCafe.toString())
                    cafeView.findViewById<TextView>(R.id.subscribe_name).text = subCafe?.subTicketDto?.subTicketName
                    val subTicketPrice = subCafe?.subTicketDto?.subTicketPrice?.toString() ?: ""
                    val paymentMethod = subCafe?.paymentDto?.paymentMethod ?: ""
                    cafeView.findViewById<TextView>(R.id.sub_info).text = "$subTicketPrice, $paymentMethod"
                }
            }

            override fun onFailure(call: Call<SubscribeHistoryDTO>, t: Throwable) {
                Log.d("mySubHomeActivity", "error")
            }
        })
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun bindingCafeImg(cafe: SubCafe, cafeView: View) {
        val imageView = cafeView.findViewById<ImageView>(R.id.sub_cafe_image)
        if (cafe.cafeImages.isNotEmpty()) {
            masterApplication.service.getFile(cafe.cafeImages[0]).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // 이미지 다운로드 및 표시
                        val inputStream = response.body()?.byteStream()
                        if (inputStream != null) {
                            GlobalScope.launch(Dispatchers.Main) {
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                imageView.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("SearchingStores", "failed loading cafe image", t)
                }
            })

        }
    }
    private fun initialize() {
        binding = ActivityMySubscriptionHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)

        binding.goBackButton.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }
    }
}