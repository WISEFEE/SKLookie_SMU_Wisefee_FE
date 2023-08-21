package com.example.wisefee

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8082/"

    val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dHRlZWVzc3MxMjNAYWFhLmNvbSIsInVzZXJJZCI6Miwibmlja25hbWUiOiJUb20iLCJhdXRoIjoiUk9MRV9DT05TVU1FUiIsImV4cCI6MTY5MjY3ODcyOH0.AZHE25z-yCBt-F89fiUpl6cleLFU8Afc9YOpstBVw2c"
    // Initialize Network Interceptor
    val networkInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = chain.proceed(newRequest)

        response.newBuilder().build()
    }

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(networkInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: RetrofitService = retrofit.create(RetrofitService::class.java)
}
