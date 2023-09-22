package com.example.wisefee.Store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityStoreMainBinding

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}