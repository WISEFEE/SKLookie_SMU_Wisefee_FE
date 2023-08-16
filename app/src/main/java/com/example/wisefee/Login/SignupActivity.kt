package com.example.wisefee.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}