package com.example.wisefee_return

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityQrPopup2Binding

class QR_popup2 : AppCompatActivity() {
    private lateinit var binding: ActivityQrPopup2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrPopup2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}