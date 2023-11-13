package com.example.wisefee.Cart

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.jwtDecoding
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
import okhttp3.ResponseBody
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
                if (response.isSuccessful) {
                    val cartProductInfo = response.body()
                    if (cartProductInfo != null) {
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
        cartAdapter = CartAdapter(products)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        masterApplication.service.getCartTotalPrice(getUserId())
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val price = response.body()?.string()?.toIntOrNull()
                        if (price != null) {
                            binding.priceTextView.text = "${price.toString()}원"
                            // TODO 보증금, 구독권할인 반영 금액해서 업뎃
                            binding.totalPriceTextView.text = "${(price.toInt() + 1000).toString() }원"
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("CartActivity", "error")
                }

            })
        // TODO 보증금 가격 정해지면 업뎃
        binding.depositTextView.text = "${"1000"}원"
        binding.countTextView.text = "총 ${products.size}개"

        binding.goBackButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("cafeId", cafeId)
            startActivity(intent)
        }

        binding.checkoutButton.setOnClickListener {
            showBuyDialog()
        }
    }

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
        val decodeClaims = jwtDecoding(jwtToken)
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

