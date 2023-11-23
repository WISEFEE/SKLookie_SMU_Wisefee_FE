package com.example.wisefee.dto

import java.io.Serializable

data class Cafe(
    val cafeId: Int,
    val title: String,
    val content: String,
    val cafePhone: String,
    val cafeImages: List<Int>,
    val addressId: Int
): Serializable

data class SearchStoresResponseDTO(
    val hasNext: Boolean,
    val cafes: List<Cafe>
): Serializable
