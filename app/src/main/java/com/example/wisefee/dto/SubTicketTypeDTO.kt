package com.example.wisefee.dto

import java.io.Serializable

data class SubTicketTypeDTO(
    val subTicketId: Int,
    val subTicketName: String,
    val subTicketPrice: String,
    val subTicketMinUserCount: Int,
    val subTicketMaxUserCount: Int,
    val subTicketDeposit: Int,
    val subTicketBaseDiscountRate: Int,
    val subTicketAdditionalDiscountRate: Int,
    val subTicketMaxDiscountRate: Int,
    val subTicketDescription: String,
): Serializable
