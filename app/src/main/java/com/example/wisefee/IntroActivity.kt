package com.example.wisefee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.wisefee.Boarding.InitActivity
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.Login.SignupActivity
import com.example.wisefee.Menu.MenuActivity
import com.example.wisefee.Store.StoreActivity
import com.example.wisefee.Store.StoreSelfServiceActivity
import com.example.wisefee.databinding.ActivityIntroBinding
import com.example.wisefee.databinding.ActivitySignupBinding
import org.mozilla.javascript.tools.jsc.Main


class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var handler = Handler(Looper.getMainLooper())

        val sp1 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isStart = sp1.getBoolean("start", false)

        if (isStart == false) {
            handler.postDelayed({
                var intent = Intent(this, InitActivity::class.java)
                startActivity(intent)
                finish()  // 현재 액티비티를 종료
            }, 1000)
        }

        else {
            if ((application as MasterApplication).checkIsLogin()) {
                handler.postDelayed({
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }, 1000)
            } else {
                handler.postDelayed({
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }, 1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}