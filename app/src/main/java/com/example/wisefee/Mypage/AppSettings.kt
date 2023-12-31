package com.example.wisefee.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityAppSettingsBinding


class AppSettings : AppCompatActivity() {
    private lateinit var binding: ActivityAppSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goBackButton.setOnClickListener { onBackPressed() }

    }
}





