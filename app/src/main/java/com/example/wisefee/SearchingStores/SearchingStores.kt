package com.example.wisefee.SearchingStores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivitySearchingStoresBinding

class SearchingStores : AppCompatActivity() {
    private lateinit var binding: ActivitySearchingStoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchingStoresBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}