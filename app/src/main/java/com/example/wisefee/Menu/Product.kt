package com.example.wisefee.Menu

import java.io.Serializable

data class ProductOptionChoice(
    val productOptionChoiceId: Int,
    val productOptionChoiceName: String,
    val productOptionChoicePrice: Double
)

data class ProductOption(
    val productOptionChoice: List<ProductOptionChoice>,
    val productOptionId: Int,
    val productOptionName: String
)

data class Product(
    val productId: Int,
    val productInfo: String,
    val productName: String,
    val productOptions: List<ProductOption>,
    val productPrice: Double
)

data class ProductList(
    val products: List<Product>
)
