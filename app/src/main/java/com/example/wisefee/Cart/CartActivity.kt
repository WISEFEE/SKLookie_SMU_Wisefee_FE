package com.example.wisefee.Cart

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.MainActivity
import com.example.wisefee.Menu.MenuActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.databinding.ActivityCartBinding
import com.example.wisefee.databinding.BuyDialogConfirmBinding
import com.example.wisefee.Payment.PaymentActivity
import com.example.wisefee.R
import com.example.wisefee.RetrofitClient
import com.example.wisefee.RetrofitClient.apiService
import com.example.wisefee.Return.ReturnTumblerActivity

class CartActivity : AppCompatActivity() {
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var cartAdapter: CartAdapter
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* API
       val apiService = RetrofitClient.apiService
        cartAdapter = CartAdapter(cartItems)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        val memberId = "1"
        getCart(memberId)
        */

        // 기존의 cartItems를 불러온다.
        val existingCartItems = intent.getSerializableExtra("cartItems") as? ArrayList<CartItem>
        Log.i("cart", "이건 뭘까요? $existingCartItems")
        if (existingCartItems != null) {
            cartItems.addAll(existingCartItems)
        }

        cartAdapter = CartAdapter(cartItems)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        // 뒤로가기 버튼
        binding.goBackButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java) //갈곳
            intent.putExtra("cartItems", existingCartItems) //현재 장바구니에 담겨진 항목들
            startActivity(intent) //인텐트 이동
        }

        // 구매버튼
        binding.checkoutButton.setOnClickListener {
            showBuyDialog()
        }

        updateTotalPrice() // 초기화 시 총 금액 업데이트

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        // 각각 Activity 들 여기에 연결해주세요.
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

    }

    //초기화 시 총 금액 업데이트
    private fun updateTotalPrice() {
        var totalPrice = 0
        var total = 0
        var quantity = 0
        for (cartItem in cartItems) {
            totalPrice += cartItem.product.productPrice * cartItem.quantity
            quantity += cartItem.quantity
        }
        total = totalPrice + 1000
        binding.priceTextView.text = "${totalPrice}원"
        binding.totalPriceTextView.text = "${total}원"
        binding.countTextView.text = "총 ${quantity}개"
    }

    //구매여부 버튼
    private fun showBuyDialog(){
        val dialogBinding = BuyDialogConfirmBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)

        val alertDialog = dialogBuilder.create()

        dialogBinding.btnYes.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java )
            startActivity(intent)
        }

        dialogBinding.btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}