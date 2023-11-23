package com.example.wisefee.Subscribe

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.dto.Cafe
import com.example.wisefee.dto.SubTicketTypeDTO
import com.example.wisefee.dto.SubscribeRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscribeDetailActivity : AppCompatActivity() {
    private lateinit var masterApplication: MasterApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cafeView = layoutInflater.inflate(R.layout.activity_subscription_detail , null)

        val cafe = intent.getSerializableExtra("cafe") as Cafe?
        val subTicket = intent.getSerializableExtra("subTicket") as SubTicketTypeDTO?


        initialize(cafe!!, cafeView)
        bindingContents(cafe, subTicket!!, cafeView)
        subscribe(cafe, subTicket, cafeView)
    }

    private fun subscribe(cafe: Cafe, subTicket: SubTicketTypeDTO, cafeView: View) {
        val subscribeInfo = SubscribeRequestDTO(
            paymentMethod = "Test",
            subComment = "Test",
            subPeople = 10
        )
        Log.d("testtest", cafe.cafeId.toString())
        Log.d("testtest", subTicket.subTicketId.toString())
        Log.d("testtest", subscribeInfo.toString())

        cafeView.findViewById<Button>(R.id.payment_button).setOnClickListener {
            masterApplication.service.addSubscribe(cafe.cafeId, subTicket.subTicketId, subscribeInfo).enqueue(object:
                Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SubscribeDetailActivity, "구독 되었습니다.", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@SubscribeDetailActivity, MyPageActivity::class.java))
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("SubscribeDetailActivity", "error")
                }

            })

        }
    }

    private fun bindingContents(cafe: Cafe, subTicket: SubTicketTypeDTO, cafeView: View) {
        bindingCafeImg(cafe, cafeView)
        cafeView.findViewById<TextView>(R.id.cafe_name).text = cafe.title
        cafeView.findViewById<TextView>(R.id.subTicketId_1).text = subTicket.subTicketName
        cafeView.findViewById<TextView>(R.id.subTicketPrice_1).text = "₩${subTicket.subTicketPrice}/월"
        cafeView.findViewById<TextView>(R.id.headcount_1).text = "${subTicket.subTicketMinUserCount} ~ ${subTicket.subTicketMaxUserCount}명"
        cafeView.findViewById<TextView>(R.id.discount_rate_1).text = "${subTicket.subTicketBaseDiscountRate}%"
        cafeView.findViewById<TextView>(R.id.description_sub).text = subTicket.subTicketDescription
    }

    private fun bindingCafeImg(cafe: Cafe, cafeView: View) {
        val imageView = cafeView.findViewById<ImageView>(R.id.cafe_image)

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
                    Log.d("SubscribeDetailActivity", "failed loading cafe image", t)
                }
            })
        }
    }

    private fun initialize(cafe: Cafe, cafeView: View) {
        setContentView(cafeView)
        masterApplication = application as MasterApplication

        cafeView.findViewById<ImageView>(R.id.home).setOnClickListener {startActivity(Intent(this, MainActivity::class.java))}
        cafeView.findViewById<ImageView>(R.id.rental).setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        cafeView.findViewById<ImageView>(R.id.return_tumbler).setOnClickListener { startActivity(
            Intent(this, ReturnTumblerActivity::class.java)
        ) }
        cafeView.findViewById<ImageView>(R.id.mypage).setImageResource(R.drawable.clicked_mypage)
        cafeView.findViewById<ImageView>(R.id.go_back_button).setOnClickListener {
            val intent = Intent(this, SubscribeListActivity::class.java)
            intent.putExtra("cafe", cafe)
            startActivity(intent)
        }

    }

}