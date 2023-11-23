package com.example.wisefee.Cart

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
import com.example.wisefee.dto.SubscribeHistory
import com.example.wisefee.dto.SubscribeHistoryDTO
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

        masterApplication.service.getSubscribeHistory().enqueue(object :
            Callback<SubscribeHistoryDTO> {
            override fun onResponse(
                call: Call<SubscribeHistoryDTO>,
                response: Response<SubscribeHistoryDTO>
            ) {
                if (response.isSuccessful) {

                    val subCafe = response.body()
                    if (subCafe != null) {
                        if (subCafe.subscribes.isNotEmpty()) {
                            val subCafeInfo = subCafe.subscribes[0]
                            // 구독 했을 때
                            calcCartWithSub(subCafeInfo)
                        } else {
                            // 구독 안했을 때
                            calcCartWithoutSub()
                        }
                    }
                }
            }

            private fun calcCartWithoutSub() {
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
                                    binding.totalPriceTextView.text =
                                        "${(price.toInt() + 1000).toString()}원"

                                    binding.depositTextView.text = "${"1000"}원"
                                    binding.countTextView.text = "총 ${products.size}개"

                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.d("CartActivity", "error")
                        }

                    })
            }

            private fun calcCartWithSub(subCafeInfo: SubscribeHistory) {
                masterApplication.service.getCartTotalPriceWithSubscribe(
                    getUserId(),
                    subCafeInfo.subId
                )
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                val price = response.body()?.string()?.toIntOrNull()
                                if (price != null) {
                                    binding.priceTextView.text = "${price.toString()}원"
                                    binding.totalPriceTextView.text =
                                        "${(price.toInt()).toString()}원"
                                    binding.depositTextView.text = "${"0"}원"
                                    binding.countTextView.text = "총 ${products.size}개"
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.d("CartActivity", "error")
                        }
                    })
            }

            override fun onFailure(call: Call<SubscribeHistoryDTO>, t: Throwable) {
                Log.d("mySubHomeActivity", "error")
            }
        })

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

