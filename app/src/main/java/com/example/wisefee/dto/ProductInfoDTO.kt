package com.example.wisefee.dto

import java.io.Serializable

data class ProductInfoDTO(
    val products: List<Product>
): Serializable

data class  Product(
    val productId: Int,
    val productInfo: String,
    val productName: String,
    val productOptions: List<ProductOption>?,
    val productPrice: Int
) : Serializable

data class ProductOption(
    val productOptionId: Int,
    val productOptionName: String,
    val productOptChoice: List<ProductOptionChoice>?
) : Serializable

data class ProductOptionChoice(
    val productOptionChoiceId: Int,
    val productOptionChoiceName: String,
    val productOptionChoicePrice: Int
) : Serializable
