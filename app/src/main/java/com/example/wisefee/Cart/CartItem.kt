package com.example.wisefee.Cart

import com.example.wisefee.Menu.Product

import java.io.Serializable

data class CartItem(
    val product: Product,
    val quantity: Int
) : Serializable
