package com.example.wisefee

import com.example.wisefee.Menu.Product
import com.example.wisefee.Cart.Product
import com.example.wisefee.Login.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("api/v1/consumer/{cafeId}/product")
    fun getProductsForCafe(@Path("cafeId") cafeId: Int): Call<List<Product>>

    @POST("/api/v1/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

}