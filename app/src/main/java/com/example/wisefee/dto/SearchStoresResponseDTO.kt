package com.example.wisefee.dto

import java.io.Serializable

data class Cafe(
    val cafeId: Int,
    val title: String,
    val content: String,
    val cafePhone: String,
    val cafeImages: List<String>,
    val addressId: Int
)

data class SearchStoresResponseDTO(
    val hasNext: Boolean,
    val cafes: List<Cafe>
): Serializable
