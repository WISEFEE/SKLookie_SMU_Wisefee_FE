package com.example.wisefee.Subscribe

import java.io.Serializable

data class Store(
    val addrId: Int,
    val storeTitle: String,
    val addressName: String,
    /*val createdAt: String,
    val region1DepthName: String,
    val region2DepthName: String,
    val region3DepthName: String,
    val updatedAt: String,
    val x: Int,
    val y: Int*/
) : Serializable
