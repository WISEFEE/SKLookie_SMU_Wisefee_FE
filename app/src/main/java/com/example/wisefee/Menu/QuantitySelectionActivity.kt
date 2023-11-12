package com.example.wisefee.Menu

import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.wisefee.Cart.CartActivity
import com.example.wisefee.Jwt_decoding
import com.example.wisefee.MasterApplication
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityQuantitySelectionBinding
import com.example.wisefee.dto.CartProductRequestDTO
import com.example.wisefee.dto.Product
import com.example.wisefee.dto.ProductOption
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuantitySelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuantitySelectionBinding
    private val selectedOptionIds = mutableMapOf<String, Int>()
    private lateinit var masterApplication: MasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuantitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()


        val intent = intent
        val product = intent.getSerializableExtra("product") as? Product
        val cafeId = intent.getIntExtra("cafeId", 0)
        if (product != null)
            getCartProducts(product, cafeId, this@QuantitySelectionActivity)
    }


    private fun initialize() {
        binding.home.setOnClickListener { finish() } // Use finish() to close the activity
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        masterApplication = application as MasterApplication
    }

    private fun getCartProducts(product: Product, cafeId: Int, activity: QuantitySelectionActivity) {
        binding.productNameTextView.text = product.productName
        binding.productPriceTextView.text = "${product.productPrice} 원"
        createDynamicRadioGroups(product.productOptions!!)
        setCartAddButtonListener(product.productId, cafeId, activity)
    }

    private fun createDynamicRadioGroups(products: List<ProductOption>) {
        val radioGroupContainer = findViewById<LinearLayout>(R.id.dynamicRadioGroupsContainer)

        val radioGroups = products.map { product ->
            Pair(product.productOptionName, product.productOptChoice!!.map { it.productOptionChoiceId to it.productOptionChoiceName })
        }

        for ((groupName, options) in radioGroups) {
            val radioGroup = createRadioGroup(options, groupName)
            radioGroupContainer.addView(radioGroup)
        }
    }

    private fun createRadioGroup(options: List<Pair<Int, String>>, groupName: String): LinearLayout {
        val groupContainer = LinearLayout(this)
        groupContainer.orientation = LinearLayout.VERTICAL
        val groupNameTextView = createGroupNameTextView(groupName)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = 20.dpToPx()
        params.bottomMargin = 20.dpToPx()
        groupNameTextView.layoutParams = params
        groupContainer.addView(groupNameTextView)

        val radioGroup = RadioGroup(this)
        radioGroup.id = View.generateViewId()
        radioGroup.orientation = RadioGroup.HORIZONTAL
        radioGroup.gravity = Gravity.CENTER

        for ((optionId, optionName) in options) {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = optionName
            radioButton.textSize = 15f
            val radioParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )
            radioParams.setMargins(0, 0, 10.dpToPx(), 10.dpToPx())
            radioButton.layoutParams = radioParams

            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedOptionIds[groupName] = optionId
                }
            }

            radioGroup.addView(radioButton)
        }

        groupContainer.addView(radioGroup)
        addSeparatorView(groupContainer)

        return groupContainer
    }

    private fun setCartAddButtonListener(productId: Int, cafeId: Int, activity: QuantitySelectionActivity) {
        val cartAddButton = findViewById<Button>(R.id.cartAddButton)
        val quantityEditText = findViewById<EditText>(R.id.quantityEditText)

        cartAddButton.setOnClickListener {
            val quantity = quantityEditText.text.toString().trim()
            if (quantity.isEmpty()) {
                // Quantity is empty, show a toast message
                showToast("수량을 입력해주세요.")
                return@setOnClickListener
            }

            // Check if quantity is a valid number
            val quantityValue = quantity.toIntOrNull()
            if (quantityValue == null || quantityValue <= 0) {
                // Invalid quantity, show a toast message
                showToast("유효한 수량을 입력해주세요.")
                return@setOnClickListener
            }

            // Continue processing with the valid quantity
            printSelectedValues(productId, cafeId, quantityValue, activity)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
    private fun getSelectedValues(): Map<String, Int> {
        return selectedOptionIds.toMap()
    }

    private fun printSelectedValues(productId: Int, cafeId: Int, quantity: Int, activity: QuantitySelectionActivity) {
        val selected = getSelectedValues()
        val productOptChoices = mutableListOf<Int>()
        for ((groupName, selectedOptionId) in selected) {
            productOptChoices.add(selectedOptionId)
            Log.d("selected_radio", "Group: $groupName, Selected Option: $selectedOptionId")
        }

        val cartProductInfoDTO = CartProductRequestDTO(
            cafeId = cafeId,
            productId = productId,
            productOptChoices = productOptChoices,
            productQuantity = quantity
        )
        masterApplication.service.addCartProduct(getUserId(), cartProductInfoDTO)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) showSuccessMessageAndNavigateToCart(activity, cafeId)
                    else handleErrorResponse()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("addCartProduct", "error")
                }
            })
    }
    private fun showSuccessMessageAndNavigateToCart(activity: QuantitySelectionActivity, cafeId: Int) {
        Toast.makeText(activity, "장바구니에 상품을 추가했습니다.", Toast.LENGTH_LONG).show()
        val intent = Intent(activity, CartActivity::class.java)
        intent.putExtra("cafeId", cafeId) // cafeId를 Intent에 추가
        activity.startActivity(intent)
    }

    private fun handleErrorResponse() {
        Log.d("addCartProduct", "error")
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
    private fun createGroupNameTextView(groupName: String): TextView {
        return TextView(this).apply {
            text = groupName
            textSize = 17f
            setTextColor(ContextCompat.getColor(this@QuantitySelectionActivity, R.color.blue))
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 25.dpToPx()
                leftMargin = 20.dpToPx()
            }
        }
    }

    private fun addSeparatorView(container: LinearLayout) {
        val separatorView = View(this)
        separatorView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.dpToPx()
        )
        separatorView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))

        container.addView(separatorView)
    }

    private fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}