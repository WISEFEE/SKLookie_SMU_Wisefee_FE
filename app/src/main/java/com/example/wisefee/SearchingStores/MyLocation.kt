package com.example.wisefee.SearchingStores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityMyLocationBinding

class MyLocation : AppCompatActivity() {
    private lateinit var binding: ActivityMyLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}


