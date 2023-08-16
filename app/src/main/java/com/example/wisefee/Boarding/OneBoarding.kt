package com.example.wisefee.Boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wisefee.databinding.ActivityOneBoardingBinding

class OneBoarding : Fragment() {
    private var _binding : ActivityOneBoardingBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = ActivityOneBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }
}