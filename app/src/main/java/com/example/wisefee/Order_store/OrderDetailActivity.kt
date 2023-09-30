package com.example.wisefee.Order_store

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.databinding.ActivityOrderDetailBinding


class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //주문 상태 스피너
        val orderStateData = mutableListOf("접수 대기", "수령 완료")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, orderStateData)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.orderStateSpinner.adapter = adapter1

        // 스피너에서 항목을 선택했을 때
        binding.orderStateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedMenu = orderStateData[position]
                    Toast.makeText(
                        applicationContext,
                        binding.orderStateSpinner.getItemAtPosition(position) as String + "이 선택되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        //예상 완료 시간 스피너
        val completionTime = mutableListOf("12:00", "12:30","1:00","1:30","2:00","2:30")

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, completionTime)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.orderCompletionTime.adapter = adapter2

        // 스피너에서 항목을 선택했을 때
        binding.orderCompletionTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val completionTime = completionTime[position]
                    Toast.makeText(
                        applicationContext,
                        binding.orderCompletionTime.getItemAtPosition(position) as String + "이 선택되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }
}