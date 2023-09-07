package com.example.wisefee.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.MainActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.SearchingStores.SearchingStores
import com.example.wisefee.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myAppSettings.setOnClickListener { startActivity(Intent(this, AppSettings::class.java)) }
        binding.mySubscriptions.setOnClickListener { startActivity(Intent(this, MySubscription_home::class.java)) }
        binding.myPaymentHistory.setOnClickListener { startActivity(Intent(this, PaymentHistory::class.java)) }
        binding.customerInquiry.setOnClickListener { startActivity(Intent(this, CustomerInquiry::class.java)) }

        binding.home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
        binding.rental.setOnClickListener {
            startActivity(Intent(this, SearchingStores::class.java))
            this.finish()
        }
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java))
            this.finish()
        }
        binding.mypage.setImageResource(R.drawable.clicked_mypage)

    }

    override fun onPause() {
        super.onPause()
    }
}