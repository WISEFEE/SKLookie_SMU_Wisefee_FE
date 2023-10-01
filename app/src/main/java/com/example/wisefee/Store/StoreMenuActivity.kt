package com.example.wisefee.Store

import MenuData
import StoreMenuAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog


class StoreMenuActivity : AppCompatActivity() {

    private lateinit var leftArrowButton: ImageButton
    private lateinit var categoryEditButton: Button
    private lateinit var categoryOrderEditButton: Button
    private lateinit var menuDropdown: Spinner
    private lateinit var menuList: ListView
    private lateinit var addButton: Button
    private lateinit var dropDownAddButton: Button

    private var menuItems = mutableListOf("COFFEE", "FOOD", "PRODUCT")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        leftArrowButton = findViewById(R.id.leftArrowButton)
        categoryEditButton = findViewById(R.id.categoryEditButton)
        categoryOrderEditButton = findViewById(R.id.categoryOrderEditButton)
        menuDropdown = findViewById(R.id.menuDropdown)
        menuList = findViewById(R.id.menuList)
        dropDownAddButton = findViewById(R.id.dropDownAddButton)

//        val customAdapter = CustomSpinnerAdapter(this, R.layout.custom_spinner_item, menuItems)
//
//        val spinner = findViewById<Spinner>(R.id.menuDropdown)
//        spinner.adapter = customAdapter

        // 드롭다운 메뉴 아이템 설정
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menuItems)
//        menuDropdown.adapter = adapter

        val customAdapter = CustomSpinnerAdapter(this, menuItems)
        val spinner = findViewById<Spinner>(R.id.menuDropdown)
        spinner.adapter = customAdapter

        // 드롭다운 메뉴 선택 이벤트 처리
        menuDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 메뉴에 따라 메뉴 목록을 업데이트
                val selectedMenu = menuItems[position]
                updateMenuList(selectedMenu)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않았을 때의 처리
            }
        }

        // 초기 메뉴 목록 설정
        updateMenuList(menuItems[0])

        dropDownAddButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("카테고리명 추가")
            alertDialog.setMessage("추가할 카테고리 이름을 입력하세요: ")

            val input = EditText(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = layoutParams
            alertDialog.setView(input)

            alertDialog.setPositiveButton("추가") { dialog, _ ->
                val newMenuName = input.text.toString().trim()
                if (newMenuName.isNotEmpty()) {
                    menuItems.add(newMenuName) // 메뉴 아이템 목록에 추가
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menuItems)
                    menuDropdown.adapter = adapter
                    menuDropdown.setSelection(menuItems.indexOf(newMenuName)) // 새로운 메뉴 선택
                }
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            alertDialog.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }



            alertDialog.show()
        }

    }

    private fun updateMenuList(selectedMenu: String) {
        // 선택된 메뉴에 따라 메뉴 목록을 업데이트
        val menuListData = when (selectedMenu) {
            "COFFEE" -> {
                arrayOf(
                    MenuData("에스프레소", "진한 커피의 대표", R.drawable.coffee_ex),
                    MenuData("카페 라떼", "에스프레소와 스팀밀크", R.drawable.coffee_ex),
                    MenuData("카푸치노", "에스프레소와 우유", R.drawable.coffee_ex)
                )
            }
            "FOOD" -> {
                arrayOf(
                    MenuData("햄버거", "고기와 야채의 만남", R.drawable.coffee_ex),
                    MenuData("피자", "치즈와 토마토의 조화", R.drawable.coffee_ex),
                    MenuData("파스타", "이탈리아의 맛", R.drawable.coffee_ex)
                )
            }
            "PRODUCT" -> {
                arrayOf(
                    MenuData("노트북", "휴대성과 성능을 동시에", R.drawable.coffee_ex),
                    MenuData("스마트폰", "스마트한 삶을 위해", R.drawable.coffee_ex),
                    MenuData("태블릿", "다양한 활용 가능", R.drawable.coffee_ex)
                )
            }
            else -> arrayOf() // 기본값은 빈 배열
        }

        val adapter = StoreMenuAdapter(this, menuListData)
        menuList.adapter = adapter

        menuList.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = menuListData[position]
            showEditDialog(selectedItem)
        }
    }

    private fun showEditDialog(menuData: MenuData) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(layoutInflater.inflate(R.layout.edit_menu_dialog, null))

        val nameEditText = alertDialog.findViewById<EditText>(R.id.editName)
        val descriptionEditText = alertDialog.findViewById<EditText>(R.id.editDescription)

        nameEditText?.setText(menuData.name)
        descriptionEditText?.setText(menuData.description)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "저장") { dialog, _ ->
            val newName = nameEditText?.text.toString().trim()
            val newDescription = descriptionEditText?.text.toString().trim()

            // 이름과 설명을 업데이트
            menuData.name = newName
            menuData.description = newDescription

            // 업데이트된 데이터를 어댑터에 알림
            (menuList.adapter as StoreMenuAdapter).notifyDataSetChanged()

            dialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "취소") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }
}