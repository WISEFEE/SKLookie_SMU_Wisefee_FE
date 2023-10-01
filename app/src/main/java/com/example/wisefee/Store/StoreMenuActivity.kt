package com.example.wisefee.Store

import MenuData
import StoreMenuAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.wisefee.R

@Suppress("DEPRECATION")
class StoreMenuActivity : AppCompatActivity() {

    private val EDIT_MENU_REQUEST_CODE = 1

    private lateinit var leftArrowButton: ImageButton
    private lateinit var categoryEditButton: Button
    private lateinit var categoryOrderEditButton: Button
    private lateinit var menuDropdown: Spinner
    private lateinit var menuList: ListView
    private lateinit var addButton: Button
    private lateinit var dropDownAddButton: Button

    private var menuItems = mutableListOf("COFFEE", "FOOD", "PRODUCT")
    private lateinit var menuAdapter: StoreMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        leftArrowButton = findViewById(R.id.leftArrowButton)
        categoryEditButton = findViewById(R.id.categoryEditButton)
        categoryOrderEditButton = findViewById(R.id.categoryOrderEditButton)
        menuDropdown = findViewById(R.id.menuDropdown)
        menuList = findViewById(R.id.menuList)
        dropDownAddButton = findViewById(R.id.dropDownAddButton)

        val customAdapter = CustomSpinnerAdapter(this, menuItems)
        val spinner = findViewById<Spinner>(R.id.menuDropdown)
        spinner.adapter = customAdapter

        menuDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMenu = menuItems[position]
                updateMenuList(selectedMenu)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않았을 때의 처리
            }
        }

        menuAdapter = updateMenuList(menuItems[0])

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
                    menuItems.add(newMenuName)
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menuItems)
                    menuDropdown.adapter = adapter
                    menuDropdown.setSelection(menuItems.indexOf(newMenuName))
                }
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            alertDialog.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.show()
        }

        val addMenuItemButton = findViewById<Button>(R.id.addMenuItemButton)

        // + 버튼 클릭 이벤트 처리
        addMenuItemButton.setOnClickListener {
            val selectedCategory = menuDropdown.selectedItem.toString()
            val intent = Intent(this, EditMenuActivity::class.java)
            // 선택된 카테고리 정보를 EditMenuActivity로 전달
            intent.putExtra("selectedCategory", selectedCategory)
            startActivityForResult(intent, ADD_MENU_ITEM_REQUEST_CODE)
        }
    }

    companion object {
        private const val ADD_MENU_ITEM_REQUEST_CODE = 2
    }

    private fun updateMenuList(selectedMenu: String): StoreMenuAdapter {
        val menuListData = when (selectedMenu) {
            "COFFEE" -> {
                arrayOf(
                    MenuData("에스프레소", "진한 커피의 대표", R.drawable.coffee_ex, 3000, true, "설탕 추가"),
                    MenuData("카페 라떼", "에스프레소와 스팀밀크", R.drawable.coffee_ex, 4000, false, ""),
                    MenuData("카푸치노", "에스프레소와 우유", R.drawable.coffee_ex, 3500, true, "휘핑 크림 추가")
                )
            }
            "FOOD" -> {
                arrayOf(
                    MenuData("햄버거", "고기와 야채의 만남", R.drawable.coffee_ex, 5500, false, "감자튀김 추가"),
                    MenuData("피자", "치즈와 토마토의 조화", R.drawable.coffee_ex, 8000, false, ""),
                    MenuData("파스타", "이탈리아의 맛", R.drawable.coffee_ex, 7000, false, "")
                )
            }
            "PRODUCT" -> {
                arrayOf(
                    MenuData("노트북", "휴대성과 성능을 동시에", R.drawable.coffee_ex, 1500000, false, ""),
                    MenuData("스마트폰", "스마트한 삶을 위해", R.drawable.coffee_ex, 1000000, false, "충전기 포함"),
                    MenuData("태블릿", "다양한 활용 가능", R.drawable.coffee_ex, 700000, false, "")
                )
            }
            else -> arrayOf()
        }

        menuAdapter = StoreMenuAdapter(this, menuListData)
        menuList.adapter = menuAdapter

        menuList.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = menuListData[position]
            showEditDialog(selectedItem)
        }


        return menuAdapter
    }

    private fun showEditDialog(menuData: MenuData) {
        val intent = Intent(this, EditMenuActivity::class.java)
        intent.putExtra("menuData", menuData)
        startActivityForResult(intent, EDIT_MENU_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_MENU_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedMenuData = data?.getSerializableExtra("newMenuData") as MenuData?
            if (updatedMenuData != null) {
                // 수정된 메뉴 데이터를 처리하고 리스트를 업데이트
                val index = menuItems.indexOfFirst { it == updatedMenuData.name }
                if (index != -1) {
                    menuItems[index] = updatedMenuData.name
                }
                // Adapter 업데이트
                menuAdapter = updateMenuList(menuDropdown.selectedItem.toString())
                menuList.adapter = menuAdapter
            }
        }
    }
}
