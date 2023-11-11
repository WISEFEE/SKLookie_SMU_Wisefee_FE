package com.example.wisefee.Boarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wisefee.IntroActivity
import com.example.wisefee.Login.LoginActivity
import com.example.wisefee.MainActivity
import com.example.wisefee.SearchingStores.SearchingStores
import com.example.wisefee.databinding.ActivityThreeBoardingBinding

class ThreeBoarding : Fragment() {
    private var _binding : ActivityThreeBoardingBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityThreeBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // startbtn 클릭 리스너 추가
        binding.startbtn.setOnClickListener {
            // MainActivity를 시작하기 전에 현재 프래그먼트가 활성화되어 있는지 확인
            if (!isAdded || isDetached) {
                return@setOnClickListener
            }

            startboarding()

            // MainActivity 시작
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // 프래그먼트가 종료될 때 UI 요소에 대한 참조 해제
        _binding = null
    }

    fun startboarding() {
        val sp1 = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp1.edit()

        // "start" 키에 true 저장
        editor.putBoolean("start", true)

        // 변경사항 저장
        editor.apply()

    }

}