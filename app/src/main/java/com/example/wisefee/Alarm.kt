package com.example.wisefee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.Store.StoreActivity
import com.example.wisefee.databinding.ActivityAlarmBinding
import com.example.wisefee.databinding.ActivityStoreMainBinding

class Alarm : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToStore.setOnClickListener { startActivity(Intent(this,  StoreActivity::class.java)) }
    }
}