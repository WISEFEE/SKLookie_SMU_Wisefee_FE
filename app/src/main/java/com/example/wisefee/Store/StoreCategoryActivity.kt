package com.example.wisefee.Store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityLoginBinding
import com.example.wisefee.databinding.ActivityStoreCategoryBinding

class StoreCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}