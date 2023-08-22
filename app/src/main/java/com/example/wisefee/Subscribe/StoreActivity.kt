package com.example.wisefee.Subscribe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.RetrofitClient
import com.example.wisefee.databinding.ActivityStoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var storeLocations: List<Store>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dummyStoreLocations() //이건 더미데이터 나중에 연결하면 지우기
       //fetchStoreLocations()   // 매장 위치 정보 가져오기 => 서버 연결시

        displayStoreLocations() // UI에 매장 위치 정보를 표시
    }

    /*
    private fun fetchStoreLocations() {
        // 서버에서 매장 위치 정보를 가져온다.
        val apiService = RetrofitClient.createService(ApiService::class.java)
        val call = apiService.getStoreLocations()

        call.enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.isSuccessful) {
                    storeLocations = response.body() ?: emptyList()
                } else {
                    // API 호출이 실패한 경우의 처리
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                // 네트워크 연결 실패 등의 처리
            }
        })
    }
     */


    private fun displayStoreLocations() {
        // storeLocations를 UI에 표시 (리사이클러뷰 연결)
        val recyclerView = binding.storeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val storeAdapter = StoreAdapter(storeLocations)
        recyclerView.adapter = storeAdapter
    }


    //더미데이터, 나중에 지우기
    private fun dummyStoreLocations() {
        storeLocations = listOf(
            Store(1, "스타벅스", "서울 은평구"),
            Store(2, "메가커피", "서울 서대문구"),
            Store(3, "빈커피", "서울 마포구")
            // 필요한 만큼 더미 데이터를 추가
        )
    }
}
