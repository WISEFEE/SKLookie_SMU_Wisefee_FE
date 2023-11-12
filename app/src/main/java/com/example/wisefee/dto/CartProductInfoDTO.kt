package com.example.wisefee.dto

import java.io.Serializable

data class CartProductInfoDTO(
    val cafeId: Int,
    val productId: Int,
    val productOptChoices: List<Int>,
    val productQuantity: Int
): Serializable
