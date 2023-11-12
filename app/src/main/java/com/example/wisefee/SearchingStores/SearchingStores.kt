package com.example.wisefee.SearchingStores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Menu.MenuActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.databinding.ActivitySearchingStoresBinding
import com.example.wisefee.dto.AddressInfoDTO
import com.example.wisefee.dto.Cafe
import com.example.wisefee.dto.MyPageResponseDTO
import com.example.wisefee.dto.SearchStoresResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingStores : AppCompatActivity() {
    private lateinit var binding: ActivitySearchingStoresBinding
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchingStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        initializeUI()
        bindingStores()

    }
    private fun initializeUI() {
        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }
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

            masterApplication.service.getAddress(cafe.addressId).enqueue(object : Callback<AddressInfoDTO> {
                override fun onResponse(
                    call: Call<AddressInfoDTO>,
                    response: Response<AddressInfoDTO>
                ) {
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



            cafeListContainer.addView(cafeView)
            cafeView.findViewById<LinearLayout>(R.id.storeButton).setOnClickListener {
                // storeButton 레이아웃 전체를 클릭할 때 이벤트 처리
                showPurchaseConfirmationPopup(cafe.cafeId, cafe.title)
            }

        }
    }
    private fun showPurchaseConfirmationPopup(cafeId: Int, title: String) {

        val inflater = LayoutInflater.from(this)
        val customView = inflater.inflate(R.layout.custom_popup_searchingstore, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(customView)

        val alertDialog = builder.create()

        val titleTextView = customView.findViewById<TextView>(R.id.title)
        titleTextView.text = title  // 매개변수로 전달받은 title로 설정

        val btnYes = customView.findViewById<Button>(R.id.btnYes)
        btnYes.setOnClickListener {
            // TODO 카페메뉴 구현
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)

            alertDialog.dismiss()
        }

        val btnNo = customView.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    override fun onPause() {
        super.onPause()
        finish()
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<SearchStoresResponseDTO>) {

}
