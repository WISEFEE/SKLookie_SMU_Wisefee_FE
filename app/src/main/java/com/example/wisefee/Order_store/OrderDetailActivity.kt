package com.example.wisefee.Order_store

import android.app.AlertDialog
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

        //다이얼로그
        //주문 접수 버튼
        binding.btnOrderSubmit.setOnClickListener {
            val confirmationDialog = AlertDialog.Builder(this)
                .setMessage("주문을 접수 하시겠습니까?")
                .setPositiveButton("접수") { dialog, _ ->
                    dialog.dismiss()

                    // "주문 접수가 완료되었습니다." 다이얼로그를 직접 생성하고 표시합니다.
                    val successDialog = AlertDialog.Builder(this)
                        .setMessage("주문 접수가 완료되었습니다.")
                        .setPositiveButton("확인") { innerDialog, _ ->
                            innerDialog.dismiss()
                        }
                        .create()

                    successDialog.show()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            confirmationDialog.show()
        }
        //주문 거절 버튼
        binding.btnOrderCancel.setOnClickListener {
            val confirmationDialog = AlertDialog.Builder(this)
                .setMessage("주문을 거절 하시겠습니까?")
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss()

                    // "주문 접수가 완료되었습니다." 다이얼로그를 직접 생성하고 표시합니다.
                    val successDialog = AlertDialog.Builder(this)
                        .setMessage("주문 거절이 완료되었습니다.")
                        .setPositiveButton("확인") { innerDialog, _ ->
                            innerDialog.dismiss()
                        }
                        .create()

                    successDialog.show()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            confirmationDialog.show()
        }

        //수령 알림 발송 버튼
        binding.btnSendNotification.setOnClickListener {
            val confirmationDialog = AlertDialog.Builder(this)
                .setMessage("수령 알림을 발송하시겠습니까?")
                .setPositiveButton("발송") { dialog, _ ->
                    dialog.dismiss()

                    // "주문 접수가 완료되었습니다." 다이얼로그를 직접 생성하고 표시합니다.
                    val successDialog = AlertDialog.Builder(this)
                        .setMessage("수령 알림이 발송되었습니다.")
                        .setPositiveButton("확인") { innerDialog, _ ->
                            innerDialog.dismiss()
                        }
                        .create()

                    successDialog.show()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            confirmationDialog.show()
        }
    }


}