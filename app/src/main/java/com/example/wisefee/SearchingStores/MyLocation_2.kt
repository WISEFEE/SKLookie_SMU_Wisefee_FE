package com.example.wisefee.SearchingStores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityMyLocation2Binding

class MyLocation_2 : AppCompatActivity() {
    private lateinit var binding: ActivityMyLocation2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyLocation2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}