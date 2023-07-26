package com.example.wisefee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wisefee.databinding.ActivityOneBoardingBinding
import com.example.wisefee.databinding.ActivityTwoBoardingBinding

class TwoBoarding : Fragment() {
    private var _binding : ActivityTwoBoardingBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = ActivityTwoBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }
}