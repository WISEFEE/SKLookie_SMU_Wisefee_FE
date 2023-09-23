package com.example.wisefee.Store

import MenuData
import StoreMenuAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisefee.R
import android.view.View
import android.widget.*

class StoreMenuActivity : AppCompatActivity() {

    private lateinit var leftArrowButton: ImageButton
    private lateinit var categoryEditButton: Button
    private lateinit var categoryOrderEditButton: Button
    private lateinit var menuDropdown: Spinner
    private lateinit var menuList: ListView
    private lateinit var addButton: Button

    private val menuItems = arrayOf("COFFEE", "FOOD", "PRODUCT")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        leftArrowButton = findViewById(R.id.leftArrowButton)
        categoryEditButton = findViewById(R.id.categoryEditButton)
        categoryOrderEditButton = findViewById(R.id.categoryOrderEditButton)
        menuDropdown = findViewById(R.id.menuDropdown)
        menuList = findViewById(R.id.menuList)
        addButton = findViewById(R.id.addButton)

        // 드롭다운 메뉴 아이템 설정
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menuItems)
        menuDropdown.adapter = adapter

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

        // + 버튼 클릭 이벤트 처리
        addButton.setOnClickListener {
            // 드롭다운 메뉴를 수정할 수 있는 로직을 여기에 추가
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
    }
}