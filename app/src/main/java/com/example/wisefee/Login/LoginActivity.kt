package com.example.wisefee.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.loginKakao.setOnClickListener { startActivity(Intent(this, _::class.java)) }
//        binding.loginNaver.setOnClickListener { startActivity(Intent(this, _::class.java)) }
//        binding.loginGoogle.setOnClickListener { startActivity(Intent(this, _::class.java)) }
        binding.login.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        binding.signup.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }


    }
}