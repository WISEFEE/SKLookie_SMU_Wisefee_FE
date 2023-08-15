package com.example.wisefee.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityCartBinding
import com.example.wisefee.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}