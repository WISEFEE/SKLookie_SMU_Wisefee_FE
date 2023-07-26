package com.example.wisefee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.wisefee.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        var PagerAdatper = PagerAdapter(this)
        PagerAdatper.addFragment(OneBoarding())
        PagerAdatper.addFragment(TwoBoarding())
        PagerAdatper.addFragment(ThreeBoarding())

        binding.viewpager.apply {
            adapter = PagerAdatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        TabLayoutMediator(binding.tabView, binding.viewpager) { tab, position ->

        }.attach()
    }


}