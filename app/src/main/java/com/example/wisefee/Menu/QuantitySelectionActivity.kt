package com.example.wisefee.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wisefee.Cart.CartActivity
import com.example.wisefee.Cart.CartItem
import com.example.wisefee.databinding.ActivityQuantitySelectionBinding


class QuantitySelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuantitySelectionBinding
    private var selectedProduct: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuantitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //선택한 메뉴 전달받음(menuactivity에서)
        selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        //상품 정보를 표시
        if (selectedProduct != null) {
            binding.productNameTextView.text = selectedProduct!!.productName
            val formattedPrice = "${selectedProduct!!.productPrice} 원"
            binding.productPriceTextView.text = formattedPrice
        }

        //장바구니 추가버튼(수량 입력)
        binding.cartAddButton.setOnClickListener {
            if (selectedProduct != null) {
                val quantity = binding.quantityEditText.text.toString().toIntOrNull()
                if (quantity != null && quantity > 0) {
                    addToCart(selectedProduct!!, quantity)
                } else {
                    Toast.makeText(this, "올바른 수량을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 장바구니에 상품 추가하고 장바구니 화면으로 이동
    //선택한 메뉴 cartItem에 담아서 cartActivity로 보냄
    private fun addToCart(product: Product, quantity: Int) {
        val cartItem = CartItem(product, quantity)
        val cartItems = mutableListOf(cartItem)

        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("cartItems", ArrayList(cartItems)) // ArrayList로 변환하여 전달
        startActivity(intent)
    }



}

