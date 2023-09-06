package com.example.wisefee.Store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityStoreSelfServiceBinding

class activity_store_self_service : AppCompatActivity() {

    private lateinit var binding: ActivityStoreSelfServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreSelfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}