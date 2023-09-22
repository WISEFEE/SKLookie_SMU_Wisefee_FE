package com.example.wisefee.Menu

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wisefee.Cart.CartActivity
import com.example.wisefee.Cart.CartItem
import com.example.wisefee.databinding.ActivityQuantitySelectionBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class QuantitySelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuantitySelectionBinding
    private var selectedProduct: Product? = null
    private val cartItems = mutableListOf<CartItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuantitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //선택한 메뉴 전달받음(menuactivity에서)
        selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        //현재 장바구니에 담긴 상품도 받음
        val existingCartItems = intent.getSerializableExtra("cartItems") as ArrayList<CartItem>?
        Log.i("selection", "현재 장바구니 상황 : $existingCartItems")

        //상품 정보를 표시
        if (selectedProduct != null) {
            binding.productNameTextView.text = selectedProduct!!.productName
            val formattedPrice = "${selectedProduct!!.productPrice} 원"
            binding.productPriceTextView.text = formattedPrice
        }

        //장바구니 추가버튼(수량, 온도 넘겨줌)
        binding.cartAddButton.setOnClickListener {
            if (selectedProduct != null) {
                val quantity = binding.quantityEditText.text.toString().toIntOrNull()
                val temperature = if (binding.iceButton.isChecked) "ice" else "hot"
                if (quantity != null && quantity > 0) {
                    addToCart(selectedProduct!!, quantity,temperature,existingCartItems)
                } else {
                    Toast.makeText(this, "올바른 수량을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // 장바구니에 상품 추가하고 장바구니 화면으로 이동
    //선택한 메뉴 cartItem에 담아서 cartActivity로 보냄
    private fun addToCart(product: Product, quantity: Int, temperature: String,existingCartItems: ArrayList<CartItem>?) {
        val cartItem = CartItem(product, quantity, temperature)
        //val cartItems = mutableListOf(cartItem)
        val intent = Intent(this, CartActivity::class.java)
        //val existingCartItems = intent.getSerializableExtra("cartItems") as? ArrayList<CartItem>


        //기존에 이미 담긴 메뉴인 경우, 해당 메뉴의 수량을 추가하는 방식으로 장바구니에 추가
        //기존에 담지 않았던 메뉴인 경우, 새로운 메뉴로 장바구니에 추가
        if (existingCartItems == null) {
            /*val cartItems = arrayListOf(cartItem)
            val cartIntent = Intent(this, CartActivity::class.java)
            cartIntent.putExtra("cartItems", cartItems)
            startActivity(cartIntent) // 장바구니 화면으로 이동*/
            intent.putExtra("cartItems", arrayListOf(cartItem))
        } else {
            val existingItemIndex = existingCartItems.indexOfFirst{ it.product == product && it.temperature == temperature }

            if (existingItemIndex != -1) {
                // 같은 상품이 있다면 수량 업데이트
                existingCartItems[existingItemIndex].quantity += quantity
            } else {
                // 상품이 카트에 없다면 카트에 추가
                existingCartItems.add(cartItem)
            }
            intent.putExtra("cartItems", existingCartItems)
            Log.i("selection", "장바구니 상황 : $existingCartItems")
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("장바구니를 확인 하시겠습니까?")
        alertDialogBuilder.setPositiveButton("예") { dialog, _ ->
            // 장바구니로 이동
            startActivity(intent)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("쇼핑하기") { dialog, _ ->
            finish()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}
