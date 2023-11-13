package com.example.wisefee.dto

import java.io.Serializable

data class CartProduct(
    val cafeId: Int,
    val cafeName: String,
    val productId: Int,
    val productName: String,
    val productInfo: String,
    val productPrice: Int,
    val productQuantity: Int,
    val productOptChoices: List<CartProductOption>?
) : Serializable

data class CartProductOption(
    val productOptId: Int,
    val productOptName: String,
    val productOptChoiceId: Int,
    val productOptChoiceName: String,
    val productOptChoicePrice: Int
) : Serializable
