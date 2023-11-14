package com.example.wisefee.Subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.MainActivity
import com.example.wisefee.MasterApplication
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.databinding.ActivitySubscribeBinding

class MySubscriptionNoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscribeBinding
    private lateinit var masterApplication: MasterApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribeBinding.inflate(layoutInflater)
        initialize()

    }
    private fun initialize() {
        binding = ActivitySubscribeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        masterApplication = application as MasterApplication

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)

        binding.goBackButton.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }
        binding.subscribeJoinButton.setOnClickListener { startActivity(Intent(this, SearchingSubscribeStoresActivity::class.java)) }

    }
}

