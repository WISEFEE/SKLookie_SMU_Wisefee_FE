package com.example.wisefee.Cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.databinding.CartItemLayoutBinding
import com.example.wisefee.dto.CartProduct
class CartAdapter(private val cartItems: List<CartProduct>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProduct: CartProduct) {
            binding.apply {
                menuNameTextView.text = cartProduct.productName
                quantityTextView.text = "${cartProduct.productQuantity}개"
                var choiceOptionName = ""
                var choiceOptionPrice = 0
                for (productOptionChoice in cartProduct.productOptChoices!!) {
                    choiceOptionName += "${productOptionChoice.productOptChoiceName} / "
                    choiceOptionPrice += productOptionChoice.productOptChoicePrice
                }
                menuPriceTextView.text = "${cartProduct.productQuantity * (cartProduct.productPrice + choiceOptionPrice)}원"
                choiceOption.text = choiceOptionName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
