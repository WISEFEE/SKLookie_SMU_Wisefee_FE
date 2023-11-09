package com.example.wisefee.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.Cart.CartActivity
import com.example.wisefee.Cart.CartItem
import com.example.wisefee.MainActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.RetrofitClient
import com.example.wisefee.Return.ReturnTumblerActivity
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

        //장바구니에서 넘긴 정보 받기
        var intent = intent
        val existingCartItems = intent.getSerializableExtra("cartItems") as? ArrayList<CartItem>


        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        // 각각 Activity 들 여기에 연결해주세요.
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

        //장바구니 버튼 전환
        binding.cartButton.setOnClickListener {
            //장바구니에 담겨진 상품이 있으면 intent로 전달
            Log.i("menu", "장바구니 상품 : $existingCartItems")
            intent = Intent(this, CartActivity::class.java)
            if(existingCartItems != null) {
                intent.putExtra("cartItems", existingCartItems)
            }
            startActivity(intent)
           // val intent = Intent(this, CartActivity::class.java)
            //startActivity(intent)
        }

        //메뉴 어댑터
       /* menuAdapter = MenuAdapter(menuList)

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter*/

        /* API 연결
        val cafeId = 1
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb25AYWFhLmNvbSIsInVzZXJJZCI6MSwibmlja25hbWUiOiJUb20iLCJhdXRoIjoiUk9MRV9DT05TVU1FUiIsImV4cCI6MTY5NDE2OTU2OX0.Z4WNXH9mdVftT6yTZ-lgT9uKAUZR1abj54CBc-lB_sU"
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
        })*/


        val productList = getProductInfo()
        if (productList != null) {
            menuList = productList
            menuAdapter = MenuAdapter(menuList, object : MenuAdapter.OnProductClickListener {
                override fun onProductClick(product: Product) {
                    //선택한 상품및 장바구니 데이터를 넘김.
                    quantitySelectionActivity(product, existingCartItems)
                }
            })
            binding.menuRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MenuActivity)
                adapter = menuAdapter
            }
        }

    }

    private fun quantitySelectionActivity(selectedProduct: Product, cartItems: ArrayList<CartItem>?) {       //선택한 메뉴 전달함(quantityselectionactivity로)
        val intent = Intent(this, QuantitySelectionActivity::class.java)
        intent.putExtra("selectedProduct", selectedProduct)
        //장바구니가 비어있는 상태면 해당값 전달하지 않음.(2023-09-09)
        if(cartItems != null) {
            intent.putExtra("cartItems", cartItems)
            Log.i("menu", "현재 장바구니 상황 : $cartItems")
        }
        startActivity(intent)
    }




    /*private fun setupMenuRecyclerView(menuList: List<com.example.wisefee.Menu.Product>){
        menuAdapter = MenuAdapter(menuList)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter
    }*/

    private fun getProductInfo(): List<Product> {
        // 목데이터
        return listOf(
            Product(1, "커피","아메리카노", null, 1000),
            Product(2, "커피","카페라떼", null, 2000),
            Product(3, "커피","카라멜마끼아또", null, 3000),
            Product(4, "커피","에스프레소", null, 3000),
            Product(5, "에이드","자몽에이드", null, 4000),
            Product(6, "에이드","청포도에이드", null, 4000),
            Product(7, "프라푸치노","자바칩프라푸치노", null, 4500),
            Product(8, "프라푸치노","카라멜프라푸치노", null, 4500)
            // ...
        )
    }

}

