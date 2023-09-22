package com.example.wisefee.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityCustomerInquiryBinding
import com.example.wisefee.databinding.ActivityMySubscriptionCurrentBinding

class MySubscription_current : AppCompatActivity() {
    private lateinit var binding: ActivityMySubscriptionCurrentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMySubscriptionCurrentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

