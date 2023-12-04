package com.example.wisefee.dto

import java.io.Serializable

data class CafeInfoDTO(
    val cafeId: Int,
    val title: String,
    val content: String,
    val cafePhone: String,
    val addrId: Int,
    val fileIds: List<Int>,
    val products: List<StoreProduct>,
): Serializable

data class StoreProduct(
    val productId: Int,
    val productName: String,
    val fileIds: List<Int>,
    val productPrice: Int,
    val productInfo: String,
    val productOptions: List<StoreProductOption>,
): Serializable

data class StoreProductOption(
    val productOptionId: Int,
    val productOptionName: String,
    val productOptionChoices: List<StoreProductOptionChoice>,
): Serializable

data class StoreProductOptionChoice(
    val productOptionChoiceId: Int,
    val productOptionChoiceName: String,
    val productOptionChoicePrice: Int,
): Serializable
