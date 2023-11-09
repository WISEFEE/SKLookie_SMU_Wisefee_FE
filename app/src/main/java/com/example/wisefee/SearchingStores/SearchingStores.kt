package com.example.wisefee.SearchingStores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.wisefee.MainActivity
import com.example.wisefee.Menu.MenuActivity
import com.example.wisefee.Mypage.MyPageActivity
import com.example.wisefee.R
import com.example.wisefee.Return.ReturnTumblerActivity
import com.example.wisefee.databinding.ActivitySearchingStoresBinding

class SearchingStores : AppCompatActivity() {
    private lateinit var binding: ActivitySearchingStoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchingStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storeButton.setOnClickListener {
            showPurchaseConfirmationPopup()
        }


        binding.home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        // 각각 Activity 들 여기에 연결해주세요.
        binding.rental.setColorFilter(ContextCompat.getColor(this, R.color.selection_color))
        binding.returnTumbler.setOnClickListener { startActivity(Intent(this, ReturnTumblerActivity::class.java)) }
        binding.mypage.setOnClickListener { startActivity(Intent(this, MyPageActivity::class.java)) }


    }

    private fun showPurchaseConfirmationPopup() {

        val inflater = LayoutInflater.from(this)
        val customView = inflater.inflate(R.layout.custom_popup_searchingstore, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(customView)

        val alertDialog = builder.create()

        val btnYes = customView.findViewById<Button>(R.id.btnYes)
        btnYes.setOnClickListener {
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)

            alertDialog.dismiss()
        }

        val btnNo = customView.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener {


            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}