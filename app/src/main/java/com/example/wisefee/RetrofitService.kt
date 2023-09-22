package com.example.wisefee


import com.example.wisefee.Cart.CartItem
import com.example.wisefee.Menu.ProductList
import com.example.wisefee.Login.LoginResponse
import com.example.wisefee.Login.SignUpConsumerResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("/api/v1/consumer/{cafeId}/product")
    fun getProductsForCafe(
        @Header("Authorization") authorization: String,
        @Path("cafeId") cafeId: Int): Call<ProductList>

    @POST("/api/v1/consumer/cart/{memberId}")
    fun addToCart(@Path("memberId") memberId: String, @Body cartItem: CartItem): Call<ResponseBody>

    @GET("/api/v1/consumer/cart/{memberId}")
    fun getCart(@Path("memberId") memberId: String): Call<List<CartItem>>


    @POST("/api/v1/auth/login")
    fun login ( @Body loginRequest: LoginRequest): Call<LoginResponse>
    data class LoginRequest(
        val email: String,
        val password: String)

    @POST("/api/v1/member")
    fun register( @Body signUpConsumerRequest: SignUpConsumerRequest): Call<ResponseBody>
    data class SignUpConsumerRequest(
        val email: String,
        val password: String,
        val nickname: String,
        val accountType: String,
        val authType: String,
        val birth: String,
        val isAllowPushMsg: String,
        val isAuthEmail: String,
        val phone: String,
        val phoneOffice: String)

}