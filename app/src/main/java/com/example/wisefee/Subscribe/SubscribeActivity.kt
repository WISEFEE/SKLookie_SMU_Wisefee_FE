package com.example.wisefee.Subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityMenuBinding

import com.example.wisefee.databinding.ActivitySubscribeBinding

class SubscribeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscribeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, StoreActivity::class.java)
        binding.subscribeJoinButton.setOnClickListener { startActivity(intent) }
    }
}