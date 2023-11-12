package com.example.wisefee.Cart


import com.example.wisefee.dto.Product
import java.io.Serializable

data class CartItem(
    val product: Product,
    var quantity: Int,
    var temperature: String,
) : Serializable

/*data class CartItem(
    val cafeId: Int,
    val cafeName: String,
    val cartStatus: String,
    val createdAt: String,
    val productId: Int,
    val productInfo: String,
    val productName: String,
    val productOptChoices: List<ProductOptChoice>,
    val productPrice: Int,
    val productQuantity: Int,
    val updatedAt: String
)

data class ProductOptChoice(
    val productOptChoiceId: Int,
    val productOptChoiceName: String,
    val productOptChoicePrice: Int,
    val productOptId: Int,
    val productOptName: String
)*/