package com.example.wisefee.Boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.wisefee.Boarding.OneBoarding
import com.example.wisefee.Boarding.PagerAdapter
import com.example.wisefee.Boarding.ThreeBoarding
import com.example.wisefee.Boarding.TwoBoarding
import com.example.wisefee.databinding.ActivityInitBinding
import com.google.android.material.tabs.TabLayoutMediator


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView(){
        val pagerAdapter = PagerAdapter(this)
        binding.viewpager.adapter = pagerAdapter // 뷰페이저 어댑터 설정

        pagerAdapter.addFragment(OneBoarding())
        pagerAdapter.addFragment(TwoBoarding())
        pagerAdapter.addFragment(ThreeBoarding())


        TabLayoutMediator(binding.tabView, binding.viewpager) { tab, position ->

        }.attach()

    }

}