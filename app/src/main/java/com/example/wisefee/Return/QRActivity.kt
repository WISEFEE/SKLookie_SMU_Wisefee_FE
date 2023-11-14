package com.example.wisefee_return

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.SearchingStores.SearchingStoresActivity
import com.example.wisefee.databinding.ActivityQrPopup2Binding
import com.example.wisefee.databinding.ActivityQractivityBinding

class QRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQractivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQractivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        binding.returnTumbler.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

        binding.requestButton.setOnClickListener {
            val dialogBinding = ActivityQrPopup2Binding.inflate(layoutInflater)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogBinding.root)

            val alertDialog = dialogBuilder.create()

            dialogBinding.homeBackBtn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java )
                startActivity(intent)
            }

            alertDialog.show()
        }
    }
}