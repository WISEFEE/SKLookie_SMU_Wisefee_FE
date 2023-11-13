package com.example.wisefee.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStores
import com.example.wisefee.databinding.ActivityMySubscriptionHomeBinding

class MySubscriptionHome : AppCompatActivity() {
    private lateinit var binding: ActivityMySubscriptionHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()


    }

    private fun initialize() {
        binding = ActivityMySubscriptionHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStores::class.java)) }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)

        binding.goBackButton.setOnClickListener { onBackPressed() }
    }
}