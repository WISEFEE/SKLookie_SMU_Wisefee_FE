package com.example.wisefee.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisefee.databinding.ActivityMainBinding
import com.example.wisefee.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuList: List<String> // 백엔드에서 받아온 메뉴 리스트를 여기에 할당

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuList = listOf("아메리카노", "카페라떼", "카푸치노", "마끼아또", "에스프레소")


        menuAdapter = MenuAdapter(menuList)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = menuAdapter
    }
}