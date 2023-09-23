package com.example.wisefee.Mypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wisefee.Jwt_decoding
import com.example.wisefee.databinding.ActivityCustomerInquiryBinding

class CustomerInquiry : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerInquiryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goBackButton.setOnClickListener { onBackPressed() }

    }
}