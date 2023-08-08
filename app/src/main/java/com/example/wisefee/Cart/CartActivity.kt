package com.example.wisefee.Cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var cartAdapter: CartAdapter
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cartAdapter = CartAdapter(cartItems)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter


        // 예시
        val product = Product(id = 1, name = "아메리카노", price = 1000)
        val cartItem = CartItem(product, quantity = 2)
        cartItems.add(cartItem) //추가
        cartAdapter.notifyDataSetChanged()  //리사이클뷰 변경적용
        val product2 = Product(id = 2, name = "카페라떼", price = 3000)
        val cartItem2 = CartItem(product2, quantity = 1)
        cartItems.add(cartItem2) //추가
        cartAdapter.notifyDataSetChanged()  //리사이클뷰 변경적용





        binding.checkoutButton.setOnClickListener {
            // 구매버튼
        }

        updateTotalPrice() // 초기화 시 총 금액 업데이트

    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        var total = 0
        for (cartItem in cartItems) {
            totalPrice += cartItem.product.price * cartItem.quantity
        }
        total = totalPrice + 1000
        binding.totalPriceTextView.text = "총 금액: ${total}원"
        binding.countTextView.text = "총 ${cartItems.size}개"

    }
}