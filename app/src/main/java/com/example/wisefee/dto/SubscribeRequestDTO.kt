package com.example.wisefee.dto

import java.io.Serializable

data class SubscribeRequestDTO(
    val paymentMethod: String,
    val subComment: String,
    val subPeople: Int,
):Serializable
