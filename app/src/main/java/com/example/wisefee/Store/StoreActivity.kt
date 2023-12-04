package com.example.wisefee.Store

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.wisefee.Alarm
import com.example.wisefee.MasterApplication
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityStoreMainBinding
import com.example.wisefee.dto.CafeInfoDTO
import com.example.wisefee.dto.SubCafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : AppCompatActivity() {
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cafeView = layoutInflater.inflate(R.layout.activity_store_main , null)
        initialize(cafeView);
        bindingCafeInfo(cafeView);
    }

    private fun bindingCafeInfo(cafeView: View) {
        masterApplication.service.getCafeIdByMemberId(masterApplication.getUserId()).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    bindingInfo(response.body()!!.string().toInt(), cafeView)
                } else {
                    // TODO 매장등록 시 항상 카페 등록하도록 수정해야 함.
                    Log.d("StoreActivity", "cafeId 없음")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("StoreActivity", "error")
            }

        })
    }

    private fun bindingInfo(cafeId: Int, cafeView: View) {
        masterApplication.service.getCafeInfo(cafeId).enqueue(object : Callback<CafeInfoDTO> {
            override fun onResponse(call: Call<CafeInfoDTO>, response: Response<CafeInfoDTO>) {
                if (response.isSuccessful) {
                    val cafeInfo = response.body()!!
                    cafeView.findViewById<TextView>(R.id.cafe_name).text = cafeInfo.title
                    cafeView.findViewById<TextView>(R.id.cafe_content).text = cafeInfo.content
                    bindingCafeImg(cafeInfo.fileIds[0], cafeView)
                }
            }

            private fun bindingCafeImg(cafeImgId: Int, cafeView: View) {
                val imageView = cafeView.findViewById<ImageView>(R.id.cafe_image)

                masterApplication.service.getFile(cafeImgId).enqueue(object :
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

            override fun onFailure(call: Call<CafeInfoDTO>, t: Throwable) {
                Log.d("StoreActivity", "error")
            }

        })
    }

    private fun initialize(cafeView: View) {
        setContentView(cafeView)
        masterApplication = application as MasterApplication

        // 주문관리
        cafeView.findViewById<Button>(R.id.manageOrderButton).setOnClickListener { startActivity(Intent(this, StoreSelfServiceActivity::class.java)) }
        // 메뉴관리
        cafeView.findViewById<Button>(R.id.manageMenuButton).setOnClickListener { startActivity(Intent(this, StoreMenuActivity::class.java)) }
        // 알림
        cafeView.findViewById<Button>(R.id.store_alarm).setOnClickListener { startActivity(Intent(this,  Alarm::class.java)) }
        cafeView.findViewById<ImageView>(R.id.home).setColorFilter(ContextCompat.getColor(this, R.color.selection_color))

//        binding.rental.setOnClickListener { startActivity(Intent(this, StoreMenuActivity::class.java)) }
//        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
//        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }
    }

}