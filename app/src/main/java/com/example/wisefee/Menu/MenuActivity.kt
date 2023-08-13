package com.example.wisefee.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.Cart.Product
import com.example.wisefee.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuList: List<Product>
   // private var productId by Delegates.notNull<Int>() // 화면에서 선택한 카페 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuList = getProductInfo()

        menuAdapter = MenuAdapter(menuList)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter

    }

    private fun getProductInfo(): List<Product> {
        // 목데이터
        return listOf(
            Product(1, "아메리카노",1000),
            Product(2, "카페라떼",3000),
            Product(3, "에스프레소",2000),
            // ...
        )
    }

        /*productId = intent.getIntExtra("productId")

        val apiService = RetrofitClient.create()
        val call = apiService.getProductsForCafe(productId)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    // val menuList = response.body() ?: emptyList()
                    val menuList = listOf("아메리카노", "카페라떼", "카푸치노", "마끼아또", "에스프레소")

                    setupMenuRecyclerView(menuList)
                } else {
                    // API 호출이 실패한 경우의 처리
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // 네트워크 연결 실패 등의 처리
            }
        })



    }
    private fun setupMenuRecyclerView(menuList: List<String>){
        menuAdapter = MenuAdapter(menuList)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter
    }*/

}