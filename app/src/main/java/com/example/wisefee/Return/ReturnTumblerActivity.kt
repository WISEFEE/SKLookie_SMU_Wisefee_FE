package com.example.wisefee.Return

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.SearchingStores.SearchingStores

import com.example.wisefee.databinding.ActivityReturnTumblerBinding

class ReturnTumblerActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnTumblerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnTumblerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStores::class.java)) }
        binding.returnTumbler.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}