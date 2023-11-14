package com.example.wisefee.Return

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.SearchingStores.SearchingStoresActivity

import com.example.wisefee.databinding.ActivityReturnTumblerBinding
import com.example.wisefee.databinding.ReturnDialogConfirmBinding

class ReturnTumblerActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnTumblerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnTumblerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.rental.setOnClickListener { startActivity(Intent(this, SearchingStoresActivity::class.java)) }
        binding.returnTumbler.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }

        binding.returnButton.setOnClickListener {
            val dialogBinding = ReturnDialogConfirmBinding.inflate(layoutInflater)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogBinding.root)

            val alertDialog = dialogBuilder.create()

            dialogBinding.btnYes.setOnClickListener {
                val intent = Intent(this, QR2Activity::class.java )
                startActivity(intent)
            }

            dialogBinding.btnNo.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}