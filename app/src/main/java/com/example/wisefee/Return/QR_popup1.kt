package com.example.wisefee_return

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.databinding.ActivityQrPopup1Binding

class QR_popup1 : AppCompatActivity() {
    private lateinit var binding: ActivityQrPopup1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrPopup1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}