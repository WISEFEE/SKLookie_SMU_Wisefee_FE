package com.example.wisefee.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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