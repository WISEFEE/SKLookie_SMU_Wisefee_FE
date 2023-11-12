package com.example.wisefee.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.databinding.ActivityMenuBinding
import com.example.wisefee.dto.Product
import com.example.wisefee.dto.ProductInfoDTO
import com.example.wisefee.dto.ProductOption
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val cafeId = intent.getIntExtra("cafeId", 0)

        initialize()
        getProducts(cafeId)

    }
    private fun initialize() {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

    }

    private fun getProducts(cafeId: Int) {
        masterApplication.service.getProducts(cafeId).enqueue(object : Callback<ProductInfoDTO> {
            override fun onResponse(call: Call<ProductInfoDTO>, response: Response<ProductInfoDTO>) {
                if (response.isSuccessful) {
                    val productsInfo = response.body()
                    if (productsInfo != null) {
                        displayProductList(productsInfo.products, cafeId)
                    }
                }
            }
            override fun onFailure(call: Call<ProductInfoDTO>, t: Throwable) {
                Log.e("fetchMemberInfo", "Failed to fetch member info", t)
            }
        })
    }

    private fun displayProductList(products: List<Product>, cafeId: Int) {
        menuAdapter = MenuAdapter(products, object : MenuAdapter.OnProductClickListener {
            override fun onProductClick(product: Product) {
                val productOption = product.productOptions
                val product = Product(
                    productId = product.productId,
                    productInfo = product.productInfo,
                    productName = product . productName,
                    productOptions = product.productOptions,
                    productPrice = product.productPrice,
                )
                 quantitySelectionActivity(product, cafeId)
            }
        })

        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MenuActivity)
            adapter = menuAdapter
        }
    }
    private fun quantitySelectionActivity(product: Product, cafeId: Int) {
        val intent = Intent(this, QuantitySelectionActivity::class.java)
        intent.putExtra("cafeId", cafeId)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}

