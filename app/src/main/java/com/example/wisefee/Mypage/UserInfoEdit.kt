package com.example.wisefee.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityMyPageUserInfoEditBinding

class UserInfoEdit : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageUserInfoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageUserInfoEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}