package com.example.wisefee.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.Cart.CartActivity
import com.example.wisefee.RetrofitClient
import com.example.wisefee.databinding.ActivityMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import kotlin.properties.Delegates

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuList: List<Product>
    //private var cafeId by Delegates.notNull<Int>() // 화면에서 선택한 카페 ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //menuList = getProductInfo()

        //장바구니 버튼 전환
        binding.cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        //메뉴 어댑터
       /* menuAdapter = MenuAdapter(menuList)

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter*/

        val cafeId = 1
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dHRlZWVzc3MxMjNAYWFhLmNvbSIsInVzZXJJZCI6Miwibmlja25hbWUiOiJUb20iLCJhdXRoIjoiUk9MRV9DT05TVU1FUiIsImV4cCI6MTY5MjY3ODcyOH0.AZHE25z-yCBt-F89fiUpl6cleLFU8Afc9YOpstBVw2c"
        val apiService = RetrofitClient.apiService
        val call = apiService.getProductsForCafe(token, cafeId)

        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    if (productList != null) {
                        menuList = productList.products
                        menuAdapter = MenuAdapter(menuList, object : MenuAdapter.OnProductClickListener {
                            override fun onProductClick(product: Product) {
                                quantitySelectionActivity(product) // 클릭 이벤트 처리
                            }
                        })
                        binding.menuRecyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MenuActivity)
                            adapter = menuAdapter
                        }
                    }
                    Toast.makeText(getApplicationContext(), "api 호출 성공", Toast.LENGTH_SHORT).show();
                } else {
                    // API 호출이 실패한 경우의 처리
                    Toast.makeText(getApplicationContext(), "api 호출 실패", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                // 네트워크 연결 실패 등의 처리
                Toast.makeText(getApplicationContext(), "네트워크 연결 실패", Toast.LENGTH_SHORT).show();
            }
        })



    }

    private fun quantitySelectionActivity(selectedProduct: Product) {       //선택한 메뉴 전달함(quantityselectionactivity로)
        val intent = Intent(this, QuantitySelectionActivity::class.java)
        intent.putExtra("selectedProduct", selectedProduct)
        startActivity(intent)
    }



    /*private fun setupMenuRecyclerView(menuList: List<com.example.wisefee.Menu.Product>){
        menuAdapter = MenuAdapter(menuList)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter
    }*/

    /*private fun getProductInfo(): List<Product> {
        // 목데이터
        return listOf(
            Product(1, "아메리카노",1000),
            Product(2, "카페라떼",3000),
            Product(3, "에스프레소",2000),
            Product(4, "에스프레소",2000),
            // ...
        )
    }*/

}

