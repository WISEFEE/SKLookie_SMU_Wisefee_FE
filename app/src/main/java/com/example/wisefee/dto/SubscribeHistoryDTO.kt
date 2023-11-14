package com.example.wisefee.dto

import java.io.Serializable

data class SubscribeHistoryDTO(
    val subscribes: List<SubscribeHistory>
): Serializable
data class SubscribeHistory(
    val subId: Int,
    val cafeId: Int,
    val totalPrice: Int?,
    val subComment: String,
    val subStatus: String,
    val subCnt: Int,
    val subPeople: Int,
    val subTicketDto: SubTicketDto,
    val paymentDto: PaymentDto
): Serializable

data class SubTicketDto (
    val subTicketId: Int,
    val subTicketName: String,
    val subTicketPrice: Int
): Serializable

data class PaymentDto (
    val paymentPrice: Int,
    val paymentInfo: String?,
    val paymentMethod: String
): Serializable