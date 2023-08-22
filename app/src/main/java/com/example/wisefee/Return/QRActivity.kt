package com.example.wisefee_return

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityQractivityBinding

class QRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQractivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQractivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}