package com.example.wisefee



import com.example.wisefee.Cart.CartItem
import com.example.wisefee.Login.LoginRequest
import com.example.wisefee.Login.LoginResponse
import com.example.wisefee.Login.MemberResponse
import com.example.wisefee.Login.SignUpConsumerRequest
import com.example.wisefee.Login.UpdateMemberRequest
import com.example.wisefee.dto.AddressInfoDTO
import com.example.wisefee.dto.CartProduct
import com.example.wisefee.dto.CartProductRequestDTO
import com.example.wisefee.dto.CartProductResponseDTO
import com.example.wisefee.dto.ProductInfoDTO
import com.example.wisefee.dto.SearchStoresResponseDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {
    // common
    @POST("/api/v1/auth/login")
    fun login ( @Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/v1/member")
    fun register( @Body signUpConsumerRequest: SignUpConsumerRequest): Call<ResponseBody>

    @GET("/api/v1/member/{id}")
    fun getMemberById(@Path("id") memberId: Int): Call<MemberResponse>

    @PUT("/api/v1/member/{id}")
    fun updateMember(@Body updateMemberRequest: UpdateMemberRequest, @Path("id") id: Int): Call<ResponseBody>

    // seller
    @GET("/api/v1/address/{id}")
    fun getAddress(@Path("id") addressId: Int): Call <AddressInfoDTO>

    // consumer
    @GET("/api/v1/consumer/cafe")
    fun getCafes(): Call<SearchStoresResponseDTO>
    @GET("/api/v1/consumer/cafe/{cafeId}/product")
    fun getProducts(@Path("cafeId") cafeId: Int): Call<ProductInfoDTO>
    @POST("/api/v1/consumer/cart/{memberId}")
    fun addCartProduct(@Path("memberId") memberId: Int, @Body cartProductInfo: CartProductRequestDTO): Call<ResponseBody>
    @GET("/api/v1/consumer/cart/{memberId}")
    fun getCart(@Path("memberId") memberId: Int): Call<List<CartProduct>>


}