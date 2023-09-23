package com.example.wisefee.Menu

import java.io.Serializable

data class Product(
    val productId: Int,
    val productInfo: String,
    val productName: String,
    val productOptions: List<ProductOption>?,
    val productPrice: Int
) : Serializable

data class ProductOption(
    val productOptionChoice: List<ProductOptionChoice>,
    val productOptionId: Int,
    val productOptionName: String
) : Serializable

data class ProductOptionChoice(
    val productOptionChoiceId: Int,
    val productOptionChoiceName: String,
    val productOptionChoicePrice: Double
) : Serializable

data class ProductList(
    val products: List<Product>
) : Serializable






