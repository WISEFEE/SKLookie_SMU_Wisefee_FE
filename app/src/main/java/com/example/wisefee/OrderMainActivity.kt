package com.example.wisefee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.wisefee.databinding.ActivityAlarmBinding
import com.example.wisefee.databinding.ActivityOrderMainBinding
import com.example.wisefee.databinding.ActivityStoreMainBinding


class OrderMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}