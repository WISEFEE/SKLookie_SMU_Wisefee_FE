package com.example.wisefee.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityPaymentHistory2Binding

class PaymentHistory_2 : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentHistory2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentHistory2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}