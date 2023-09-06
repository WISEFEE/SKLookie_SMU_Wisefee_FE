package com.example.wisefee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.Store.StoreActivity
import com.example.wisefee.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var handler = Handler(Looper.getMainLooper())
        if ((application as MasterApplication).checkIsLogin()) {
            handler.postDelayed({

//                var intent = Intent(this, MainActivity::class.java)

                var intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }, 1000)
        } else {
            handler.postDelayed({
                var intent = Intent(this, StoreActivity::class.java)



                startActivity(intent)
            }, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}