package com.example.wisefee_return

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import com.example.wisefee.databinding.ActivityReturnPopupBinding

class return_popup : AppCompatActivity() {
    private lateinit var binding: ActivityReturnPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnPopupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
