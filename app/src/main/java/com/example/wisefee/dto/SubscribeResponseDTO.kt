package com.example.wisefee.dto

import java.io.Serializable

data class SubscribeResponseDTO(
    val cafes: List<SubCafe>
): Serializable

data class SubCafe(
    val cafeId: Int,
    val title: String,
    val content: String,
    val cafePhone: String,
    val cafeImages: List<Int>,
    val addressId: Int
):Serializable
