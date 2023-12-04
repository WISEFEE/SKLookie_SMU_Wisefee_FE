package com.example.wisefee

import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
        //chrome://inspect/#devices
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()
            if (checkIsLogin()) {
                getUserToken().let { accessToken ->
                    val request = original.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .build()
                    it.proceed(request)
                }
            } else {
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.39.223.216/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    fun checkIsLogin(): Boolean {

        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val accessToken = sp.getString("accessToken", "null")
        return accessToken != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val accessToken = sp.getString("accessToken", "null")
        return if (accessToken == "null") null
        else accessToken
    }


    fun getUserId(): Int {
        val jwtToken = getUserToken()

        if (jwtToken == null) {
            Log.d("decode", "JWT token is null")
            return 0
        }

        // jwt decoding
        val decodeClaims = jwtDecoding(jwtToken)
        if (decodeClaims == null) {
            Log.d("decode", "Failed decoding JWT token")
            return 0
        }

        // get userId from jwt
        val userId = decodeClaims.optInt("userId")
        if (userId == 0) {
            Log.d("decode", "Invalid user id")
            return 0
        }
        return userId
    }

}