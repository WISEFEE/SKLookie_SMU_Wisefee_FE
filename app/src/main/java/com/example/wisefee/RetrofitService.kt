package com.example.wisefee

import com.example.wisefee.Menu.Product
import com.example.wisefee.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("api/v1/consumer/{cafeId}/product")
    fun getProductsForCafe(@Path("cafeId") cafeId: Int): Call<List<Product>>

    @POST("/api/v1/auth/login")
    fun login ( @Body loginRequest: LoginRequest): Call<LoginResponse>
    data class LoginRequest(val email: String, val password: String)

}