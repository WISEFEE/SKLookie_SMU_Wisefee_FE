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
        val userId = masterApplication.getUserId()
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
                masterApplication.service.getCartTotalPrice(masterApplication.getUserId())
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                val price = response.body()?.string()?.toIntOrNull()
                                if (price != null) {
                                    binding.priceTextView.text = "${price}원"
                                    binding.totalPriceTextView.text =
                                        "${(price.toInt() + 1000)}원"

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
                    masterApplication.getUserId(),
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
//                                    binding.priceTextView.text = "${price}원"
                                    // TODO 보증금 가격 뺀 가격으로 update 필요
                                    binding.totalPriceTextView.text = "${(price.toInt())}원"
                                    binding.depositTextView.text = "${"0"}원"

                                    var totalQuantity = 0
                                    for (product in products) {
                                        totalQuantity += product.productQuantity
                                    }
                                    binding.countTextView.text = "총 ${totalQuantity}개"

                                    bindingTotalPrice(price)

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

    private fun bindingTotalPrice(subPrice: Int) {
        masterApplication.service.getCartTotalPrice(masterApplication.getUserId())
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val price = response.body()?.string()?.toIntOrNull()
                        if (price != null) {
                            binding.priceTextView.text = "${price}원"
                            binding.discountMoney.text = "${price-subPrice}원"
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("CartActivity", "error")
                }

            })
    }
}

