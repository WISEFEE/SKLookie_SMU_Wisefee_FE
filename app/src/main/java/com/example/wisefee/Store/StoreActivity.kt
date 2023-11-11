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

        binding.storeAlarm.setOnClickListener { startActivity(Intent(this,  Alarm::class.java)) }
    }

}