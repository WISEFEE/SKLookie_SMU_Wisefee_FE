package com.example.wisefee.Subscribe

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.databinding.ActivitySubscriptionListBinding
import com.example.wisefee.dto.Cafe
import com.example.wisefee.dto.SubTicketTypeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscribeListActivity : AppCompatActivity() {
    private lateinit var masterApplication: MasterApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cafeView = layoutInflater.inflate(R.layout.activity_subscription_list , null)

        initialize(cafeView)
        bindingContents(cafeView)

    }

    private fun bindingContents(cafeView: View) {
        val cafe = intent.getSerializableExtra("cafe") as Cafe?

        bindingCafeImg(cafe!!, cafeView)
        bindingSubTickets(cafe, cafeView)
    }

    private fun bindingSubTickets(cafe: Cafe, cafeView: View) {
        masterApplication.service.getSubTicketType().enqueue(object :
            Callback<List<SubTicketTypeDTO>> {
            override fun onResponse(
                call: Call<List<SubTicketTypeDTO>>,
                response: Response<List<SubTicketTypeDTO>>
            ) {
                if (response.isSuccessful) {
                    val subTickets = response.body()
                    if (!subTickets.isNullOrEmpty()) {
                        val ticketNames = arrayOf(
                            R.id.sub_ticket_choice_name1,
                            R.id.sub_ticket_choice_name2,
                            R.id.sub_ticket_choice_name3
                        )
                        val ticketPrices = arrayOf(
                            R.id.sub_ticket_choice_price1,
                            R.id.sub_ticket_choice_price2,
                            R.id.sub_ticket_choice_price3
                        )

                        val userCounts = arrayOf(
                            R.id.the_number_of_available_1,
                            R.id.the_number_of_available_2,
                            R.id.the_number_of_available_3
                        )

                        val discountRates = arrayOf(
                            R.id.sub_ticket_choice_discount_rate1,
                            R.id.sub_ticket_choice_discount_rate2,
                            R.id.sub_ticket_choice_discount_rate3
                        )

                        for (i in 0 until minOf(subTickets.size, ticketNames.size)) {
                            val subTicket = subTickets[i]
                            cafeView.findViewById<TextView>(ticketNames[i]).text = subTicket.subTicketName
                            cafeView.findViewById<TextView>(ticketPrices[i]).text = "₩${subTicket.subTicketPrice}/월"
                            cafeView.findViewById<TextView>(userCounts[i]).text = "${subTicket.subTicketMinUserCount} ~ ${subTicket.subTicketMaxUserCount}명"
                            cafeView.findViewById<TextView>(discountRates[i]).text = "${subTicket.subTicketBaseDiscountRate}%"

                            cafeView.findViewById<TextView>(ticketNames[i]).setOnClickListener { handleSubTicketClick(cafe, subTicket) }
                            cafeView.findViewById<TextView>(ticketPrices[i]).setOnClickListener { handleSubTicketClick(cafe, subTicket) }
                            cafeView.findViewById<TextView>(userCounts[i]).setOnClickListener { handleSubTicketClick(cafe, subTicket) }
                            cafeView.findViewById<TextView>(discountRates[i]).setOnClickListener { handleSubTicketClick(cafe, subTicket) }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<SubTicketTypeDTO>>, t: Throwable) {
                Log.d("bindingSubTickets", "error")
            }

        })
    }

    private fun handleSubTicketClick(cafe: Cafe, subTicket: SubTicketTypeDTO) {
        val intent = Intent(this, SubscribeDetailActivity::class.java)
        intent.putExtra("subTicket", subTicket)
        intent.putExtra("cafe", cafe)
        startActivity(intent)
    }

    private fun bindingCafeImg(cafe: Cafe, cafeView: View) {
        val imageView = cafeView.findViewById<ImageView>(R.id.sub_ticket_choice_sub_cafe_image1)
        val imageView1 = cafeView.findViewById<ImageView>(R.id.sub_ticket_choice_sub_cafe_image2)
        val imageView2 = cafeView.findViewById<ImageView>(R.id.sub_ticket_choice_sub_cafe_image3)
        cafeView.findViewById<TextView>(R.id.sub_ticket_choice_cafe_name).text = cafe.title
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
                    Log.d("SubscribeListActivity", "failed loading cafe image", t)
                }
            })
        }
    }
    private fun initialize(cafeView: View) {
        setContentView(cafeView)
        masterApplication = application as MasterApplication

        cafeView.findViewById<ImageView>(R.id.home).setOnClickListener {startActivity(Intent(this, MainActivity::class.java))}
        cafeView.findViewById<ImageView>(R.id.rental).setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        cafeView.findViewById<ImageView>(R.id.return_tumbler).setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        cafeView.findViewById<ImageView>(R.id.mypage).setImageResource(R.drawable.clicked_mypage)
        cafeView.findViewById<ImageView>(R.id.go_back_button).setOnClickListener { startActivity(Intent(this, SearchingSubscribeStoresActivity::class.java)) }
    }

}