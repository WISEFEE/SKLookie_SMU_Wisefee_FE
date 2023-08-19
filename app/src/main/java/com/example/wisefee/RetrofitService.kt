package com.example.wisefee

import com.example.wisefee.Menu.Product
import com.example.wisefee.Menu.ProductList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitService {
    @GET("api/v1/consumer/{cafeId}/product")
    fun getProductsForCafe(
        @Header("Authorization") authorization: String,
        @Path("cafeId") cafeId: Int): Call<ProductList>
}