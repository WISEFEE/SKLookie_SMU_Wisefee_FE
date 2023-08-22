package com.example.wisefee.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityMySubscriptionHomeBinding

class MySubscription_home : AppCompatActivity() {
    private lateinit var binding: ActivityMySubscriptionHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMySubscriptionHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}