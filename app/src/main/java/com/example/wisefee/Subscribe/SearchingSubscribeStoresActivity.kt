package com.example.wisefee.Subscribe

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.databinding.ActivitySearchingStoresSubscribeBinding
import com.example.wisefee.dto.AddressInfoDTO
import com.example.wisefee.dto.Cafe
import com.example.wisefee.dto.SearchStoresResponseDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchingSubscribeStoresActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchingStoresSubscribeBinding
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        bindingStores()

    }
        // set footer
    private fun initialize() {
        binding = ActivitySearchingStoresSubscribeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)
        binding.goBackButton.setOnClickListener { startActivity(Intent(this, MySubscriptionNoActivity::class.java)) }
    }

    private fun bindingStores() {
        masterApplication.service.getCafes().enqueue(object : Callback<SearchStoresResponseDTO> {
            override fun onResponse(
                call: Call<SearchStoresResponseDTO>,
                response: Response<SearchStoresResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val searchStoresResponseDTO = response.body()
                    if (searchStoresResponseDTO != null) {
                        displayCafeList(searchStoresResponseDTO.cafes)
                    }
                }
            }
            override fun onFailure(call: Call<SearchStoresResponseDTO>, t: Throwable) {
                Log.e("fetchCafes", "error")
            }
        })
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun displayCafeList(cafes: List<Cafe>) {
        val cafeListContainer = findViewById<LinearLayout>(R.id.cafeListContainer)

        // 기존에 추가되어 있던 뷰들을 모두 제거
        cafeListContainer.removeAllViews()

        // 각 카페 정보를 동적으로 생성된 뷰로 만들어서 추가
        for (cafe in cafes) {
            val cafeView = layoutInflater.inflate(R.layout.store_layout, null)

            cafeView.findViewById<TextView>(R.id.cafe_name).text = cafe.title
            cafeView.findViewById<TextView>(R.id.content).text = cafe.content
            cafeView.findViewById<TextView>(R.id.tel).text = cafe.cafePhone
            // address binding
            bindingAddress(cafe, cafeView)
            // image binding
            bindingImage(cafe, cafeView)

            cafeListContainer.addView(cafeView)
            cafeView.findViewById<LinearLayout>(R.id.storeButton).setOnClickListener {
                val intent = Intent(this, SubscribeListActivity::class.java)
                intent.putExtra("cafe", cafe)
                startActivity(intent)
            }
        }
    }

    private fun bindingImage(cafe: Cafe, cafeView: View){
        val imageView = cafeView.findViewById<ImageView>(R.id.cafe_image)

        if (cafe.cafeImages.isNotEmpty()) {
            masterApplication.service.getFile(cafe.cafeImages[0]).enqueue(object : Callback<ResponseBody> {
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

    private fun bindingAddress(cafe: Cafe, cafeView: View) {
        masterApplication.service.getAddress(cafe.addressId).enqueue(object : Callback<AddressInfoDTO> {
            override fun onResponse(call: Call<AddressInfoDTO>, response: Response<AddressInfoDTO>) {
                if (response.isSuccessful) {
                    response.body()?.let { addressInfo ->
                        cafeView.findViewById<TextView>(R.id.address).text = addressInfo.addressDetail
                    }
                }
            }
            override fun onFailure(call: Call<AddressInfoDTO>, t: Throwable) {
                Log.e("fetchCafes", "error")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}

