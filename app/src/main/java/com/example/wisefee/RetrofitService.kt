package com.example.wisefee

import com.example.wisefee.Menu.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("api/v1/consumer/{cafeId}/product")
    fun getProductsForCafe(@Path("cafeId") cafeId: Int): Call<List<Product>>
}