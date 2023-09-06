package com.example.wisefee.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupConsumer.setOnClickListener { startActivity(Intent(this, SignUpConsumerActivity::class.java)) }
        binding.signupSeller.setOnClickListener { startActivity(Intent(this, SignUpSellerActivity::class.java)) }

    }
}