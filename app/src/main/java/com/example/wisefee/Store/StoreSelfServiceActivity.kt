package com.example.wisefee.Store

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.databinding.ActivityStoreSelfServiceBinding

class StoreSelfServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreSelfServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreSelfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.manageOrderButton.setOnClickListener {
            // 새로운 화면으로 이동하는 코드를 여기에 추가
            val intent = Intent(this, StoreSelfServiceActivity::class.java)
            startActivity(intent)
        }

        binding.manageMenuButton.setOnClickListener {
            // 새로운 화면으로 이동하는 코드를 여기에 추가
            val intent = Intent(this, StoreMenuActivity::class.java)
            startActivity(intent)
        }

        binding.home.setOnClickListener {
            // 새로운 화면으로 이동하는 코드를 여기에 추가
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }
    }
}