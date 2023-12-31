package com.example.wisefee

import com.example.wisefee.Login.LoginRequest
import com.example.wisefee.Login.LoginResponse
import com.example.wisefee.Login.MemberResponse
import com.example.wisefee.Login.SignUpConsumerRequest
import com.example.wisefee.Login.UpdateMemberRequest
import com.example.wisefee.dto.AddressInfoDTO
import com.example.wisefee.dto.Cafe
import com.example.wisefee.dto.CafeInfoDTO
import com.example.wisefee.dto.CartProduct
import com.example.wisefee.dto.CartProductRequestDTO
import com.example.wisefee.dto.ProductInfoDTO
import com.example.wisefee.dto.SearchStoresResponseDTO
import com.example.wisefee.dto.SubTicketDto
import com.example.wisefee.dto.SubTicketTypeDTO
import com.example.wisefee.dto.SubscribeHistoryDTO
import com.example.wisefee.dto.SubscribeRequestDTO
import com.example.wisefee.dto.SubscribeResponseDTO
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
    @GET("/api/v1/seller/cafeId/{memberId}")
    fun getCafeIdByMemberId(@Path("memberId") memberId: Int): Call<ResponseBody>
    @GET("/api/v1/seller/cafe/{cafeId}")
    fun getCafeInfo(@Path("cafeId") cafeId: Int): Call <CafeInfoDTO>

    // consumer
    @GET("/api/v1/consumer/cafe")
    fun getCafes(): Call<SearchStoresResponseDTO>
    @GET("/api/v1/consumer/cafe/{cafeId}/product")
    fun getProducts(@Path("cafeId") cafeId: Int): Call<ProductInfoDTO>
    @POST("/api/v1/consumer/cart/{memberId}")
    fun addCartProduct(@Path("memberId") memberId: Int, @Body cartProductInfo: CartProductRequestDTO): Call<ResponseBody>
    @GET("/api/v1/consumer/cart/{memberId}")
    fun getCart(@Path("memberId") memberId: Int): Call<List<CartProduct>>
    @GET("/api/v1/consumer/cart/price/{memberId}")
    fun getCartTotalPrice(@Path("memberId") memberId: Int): Call<ResponseBody>
    @GET("/api/v1/consumer/cart/price/sub-ticket/{memberId}")
    fun getCartTotalPriceWithSubscribe(@Path("memberId") memberId: Int, @Query("subscribeId") subscribeId: Int): Call<ResponseBody>

    @GET("/api/v1/file/{id}")
    fun getFile(@Path("id") id: Int): Call<ResponseBody>
    @GET("/api/v1/consumer/subscribe/cafe")
    fun getSubscribeInfo(): Call<SubscribeResponseDTO>
    @GET("/api/v1/consumer/subscribe")
    fun getSubscribeHistory(): Call<SubscribeHistoryDTO>
    @GET("/api/v1/subTicketType")
    fun getSubTicketType(): Call<List<SubTicketTypeDTO>>
    @POST("/api/v1/consumer/subscribe/{cafeId}/subTicketType/{subTicketTypeId}")
    fun addSubscribe(@Path("cafeId") cafeId: Int, @Path("subTicketTypeId") subTicketTypeId: Int, @Body subscribeInfo: SubscribeRequestDTO): Call<ResponseBody>
}