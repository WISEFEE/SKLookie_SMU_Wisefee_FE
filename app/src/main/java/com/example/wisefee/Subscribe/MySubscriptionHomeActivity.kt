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
import com.example.wisefee.dto.SubTicketTypeDTO
import com.example.wisefee.dto.SubscribeHistoryDTO
import com.example.wisefee.dto.SubscribeResponseDTO
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
        val cafeView = layoutInflater.inflate(R.layout.activity_my_subscription_home , null)
        initialize(cafeView)
        bindingImage(cafeView)
    }

    // TODO 사용자는 구독권 1개만 가질 수 있다고 가정하고 진행함.
    private fun bindingImage(cafeView: View){
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

        bindingCafeHistoryInfo(cafeView)
    }

    private fun bindingSubTickets(cafeView: View, subId1: Int, subId2: Int) {
//        // TODO 여기에 2단계, 3단계 구독권 ListUp하기!!
//        // subTicketName(o), subTicketPrice(o), subTicketMinUserCount ~ subTicketMaxUserCount, subTicketBaseDiscountRate,
//        // /api/v1/subTicketType 써서 넣기.
        masterApplication.service.getSubTicketType().enqueue(object :
            Callback<List<SubTicketTypeDTO>> {
            override fun onResponse(
                call: Call<List<SubTicketTypeDTO>>,
                response: Response<List<SubTicketTypeDTO>>
            ) {
                if (response.isSuccessful) {
                    val subTickets = response.body()
                    if (!subTickets.isNullOrEmpty()) {

                        cafeView.findViewById<TextView>(R.id.sub_cafe_name_2).text = subTickets[subId1].subTicketName
                        cafeView.findViewById<TextView>(R.id.sub_cafe_name_3).text = subTickets[subId2].subTicketName

                        cafeView.findViewById<TextView>(R.id.sub_cafe_price_2).text = "가격: " + subTickets[subId1].subTicketPrice
                        cafeView.findViewById<TextView>(R.id.sub_cafe_price_3).text = "가격: " + subTickets[subId2].subTicketPrice

                        cafeView.findViewById<TextView>(R.id.the_number_of_available_2).text = "인원: " + subTickets[subId1].subTicketMinUserCount + " ~ " + subTickets[subId1].subTicketMaxUserCount
                        cafeView.findViewById<TextView>(R.id.the_number_of_available_3).text = "인원: " + subTickets[subId2].subTicketMinUserCount + " ~ " + subTickets[subId2].subTicketMaxUserCount

                        cafeView.findViewById<TextView>(R.id.sub_cafe_discount_rate_2).text = "할인율: " + subTickets[subId1].subTicketBaseDiscountRate + "%"
                        cafeView.findViewById<TextView>(R.id.sub_cafe_discount_rate_3).text = "할인율: " + subTickets[subId2].subTicketBaseDiscountRate + "%"
                    }
                }
            }

            override fun onFailure(call: Call<List<SubTicketTypeDTO>>, t: Throwable) {
                Log.d("bindingSubTickets", "error")
            }

        })
    }

    private fun bindingCafeHistoryInfo(cafeView: View) {
        masterApplication.service.getSubscribeHistory().enqueue(object :
            Callback<SubscribeHistoryDTO> {
            override fun onResponse(
                call: Call<SubscribeHistoryDTO>,
                response: Response<SubscribeHistoryDTO>
            ) {
                if (response.isSuccessful) {
                    val subCafe = response.body()?.subscribes?.get(0)
                    cafeView.findViewById<TextView>(R.id.subscribe_name).text = subCafe?.subTicketDto?.subTicketName
                    val subTicketPrice = subCafe?.subTicketDto?.subTicketPrice?.toString() ?: ""
                    val paymentMethod = subCafe?.paymentDto?.paymentMethod ?: ""
                    cafeView.findViewById<TextView>(R.id.sub_info).text = "$subTicketPrice, $paymentMethod"

                    Log.d("adsfadsf", subCafe?.subId.toString())

                    // 구독한 ticket 에 따라 list 되는 구독권 지정
                    if (subCafe?.subTicketDto?.subTicketId == 1) bindingSubTickets(cafeView, 1, 2)
                    if (subCafe?.subTicketDto?.subTicketId == 2) bindingSubTickets(cafeView, 0, 2)
                    if (subCafe?.subTicketDto?.subTicketId == 3) bindingSubTickets(cafeView, 0, 1)

                }
            }

            override fun onFailure(call: Call<SubscribeHistoryDTO>, t: Throwable) {
                Log.d("mySubHomeActivity", "error")
            }
        })
    }

    private fun bindingCafeImg(cafe: SubCafe, cafeView: View) {
        val imageView = cafeView.findViewById<ImageView>(R.id.sub_cafe_image)
        val imageView1 = cafeView.findViewById<ImageView>(R.id.sub_cafe_image_2)
        val imageView2 = cafeView.findViewById<ImageView>(R.id.sub_cafe_image_3)

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
                                imageView1.setImageBitmap(bitmap)
                                imageView2.setImageBitmap(bitmap)
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
    private fun initialize(cafeView: View) {
//        binding = ActivityMySubscriptionHomeBinding.inflate(layoutInflater)
        setContentView(cafeView)
        masterApplication = application as MasterApplication

        cafeView.findViewById<ImageView>(R.id.home).setOnClickListener {startActivity(Intent(this, MainActivity::class.java))}
        cafeView.findViewById<ImageView>(R.id.rental).setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        cafeView.findViewById<ImageView>(R.id.return_tumbler).setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        cafeView.findViewById<ImageView>(R.id.mypage).setImageResource(R.drawable.clicked_mypage)
        cafeView.findViewById<ImageView>(R.id.go_back_button).setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }
    }
}