package com.example.wisefee.Store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wisefee.Alarm
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.Login.SignUpConsumerActivity
import com.example.wisefee.databinding.ActivityStoreMainBinding

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.storeAlarm.setOnClickListener { startActivity(Intent(this,  Alarm::class.java)) }
    }

}