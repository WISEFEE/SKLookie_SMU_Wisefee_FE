package com.example.wisefee.Store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.databinding.ActivityStoreSelfServiceBinding

class StoreSelfServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreSelfServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreSelfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}