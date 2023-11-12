package com.example.wisefee.Menu

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
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
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.databinding.ActivityQuantitySelectionBinding
import com.example.wisefee.dto.Product
import com.example.wisefee.dto.ProductOption


class QuantitySelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuantitySelectionBinding
    private val selectedOptionIds = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuantitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = getProductFromIntent()

        initialize()

        if (product != null) {
            getCartProducts(product)
        }
    }

    private fun getProductFromIntent(): Product? {
        val intent = intent
        return intent.getSerializableExtra("product") as? Product
    }

    private fun initialize() {
        binding.home.setOnClickListener { finish() } // Use finish() to close the activity
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        // Add other listeners as needed
    }

    private fun getCartProducts(product: Product) {
        binding.productNameTextView.text = product.productName
        binding.productPriceTextView.text = "${product.productPrice} 원"
        createDynamicRadioGroups(product.productOptions!!)
        setCartAddButtonListener()
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

    private fun setCartAddButtonListener() {
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
            printSelectedValues()
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

    private fun printSelectedValues() {
        val selected = getSelectedValues()
        for ((groupName, selectedOptionId) in selected) {
            // TODO ID들 정보가지고 장바구니 추가 API 호출.
            Log.d("selected_radio", "Group: $groupName, Selected Option: $selectedOptionId")
        }
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