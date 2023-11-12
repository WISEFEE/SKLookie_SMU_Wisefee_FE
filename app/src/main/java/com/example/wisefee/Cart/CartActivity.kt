package com.example.wisefee.Cart

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.Jwt_decoding
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Menu.MenuActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.databinding.ActivityCartBinding
import com.example.wisefee.databinding.BuyDialogConfirmBinding
import com.example.wisefee.Payment.PaymentActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.dto.CartProduct
import com.example.wisefee.dto.CartProductResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        val intent = intent
        val cafeId = intent.getIntExtra("cafeId", 0)
        getProducts(cafeId)
    }

    private fun initialize() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

    }
    private fun getProducts(cafeId: Int) {
        val userId = getUserId()
        masterApplication.service.getCart(userId).enqueue(object : Callback<List<CartProduct>> {
            override fun onResponse(
                call: Call<List<CartProduct>>,
                response: Response<List<CartProduct>>
            ) {
                Log.d("cartProduct", "4")

                if (response.isSuccessful) {
                    Log.d("cartProduct", "4")
                    val cartProductInfo = response.body()
                    if (cartProductInfo != null) {
                        Log.d("cartProduct", "5")
                        displayCartProductList(cartProductInfo, cafeId)
                    }
                } else {
                    Log.d("cartProduct", "error")
                }
            }
            override fun onFailure(call: Call<List<CartProduct>>, t: Throwable) {
                Log.e("fetchMemberInfo", "Failed to fetch member info", t)
            }
        })
    }

    private fun displayCartProductList(products: List<CartProduct>, cafeId: Int) {
        Log.d("cartProduct", products.toString())
        cartAdapter = CartAdapter(products)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        // TODO 장바구니 총금액, 개수 등 마무리 계산

        // 뒤로가기 버튼
        binding.goBackButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("cafeId", cafeId)
            startActivity(intent)
        }
        // 구매버튼
        binding.checkoutButton.setOnClickListener {
            showBuyDialog()
        }
//        updateTotalPrice() // 초기화 시 총 금액 업데이트
    }
    //초기화 시 총 금액 업데이트
//    private fun updateTotalPrice() {
//        var totalPrice = 0
//        var total = 0
//        var quantity = 0
//        for (cartItem in cartItems) {
//            totalPrice += cartItem.product.productPrice * cartItem.quantity
//            quantity += cartItem.quantity
//        }
//        total = totalPrice + 1000
//        binding.priceTextView.text = "${totalPrice}원"
//        binding.totalPriceTextView.text = "${total}원"
//        binding.countTextView.text = "총 ${quantity}개"
//    }

    //구매여부 버튼
    private fun showBuyDialog() {
        val dialogBinding = BuyDialogConfirmBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)

        val alertDialog = dialogBuilder.create()

        dialogBinding.btnYes.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        dialogBinding.btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun getUserId(): Int {
        val jwtToken = masterApplication.getUserToken()

        if (jwtToken == null) {
            Log.d("decode", "JWT token is null")
            return 0
        }

        // jwt decoding
        val decodeClaims = Jwt_decoding(jwtToken)
        if (decodeClaims == null) {
            Log.d("decode", "Failed decoding JWT token")
            return 0
        }

        // get userId from jwt
        val userId = decodeClaims.optInt("userId")
        if (userId == 0) {
            Log.d("decode", "Invalid user id")
            return 0
        }
        return userId
    }

}

