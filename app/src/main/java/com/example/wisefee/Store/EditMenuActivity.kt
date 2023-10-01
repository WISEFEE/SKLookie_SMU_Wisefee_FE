package com.example.wisefee.Store

import MenuData
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.wisefee.R

class EditMenuActivity : AppCompatActivity() {

    private lateinit var editMenuName: EditText
    private lateinit var editMenuDescription: EditText
    private lateinit var editMenuPrice: EditText
    private lateinit var checkBoxIce: CheckBox
    private lateinit var editExtraOption: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        editMenuName = findViewById(R.id.editMenuName)
        editMenuDescription = findViewById(R.id.editMenuDescription)
        editMenuPrice = findViewById(R.id.editMenuPrice)
        checkBoxIce = findViewById(R.id.checkBoxIce)
        editExtraOption = findViewById(R.id.editExtraOption)
        saveButton = findViewById(R.id.saveButton)

        // 이전 화면에서 전달한 메뉴 데이터를 받아옵니다.
        val menuData = intent.getSerializableExtra("menuData") as MenuData?

        // 메뉴 수정 화면인지, 메뉴 추가 화면인지 구분합니다.
        val isEditing = menuData != null

        if (isEditing) {
            // 메뉴 수정 화면이면, 기존 메뉴 데이터를 화면에 표시합니다.
            menuData?.let { displayMenuData(it) }
            title = "메뉴 수정"
        } else {
            title = "메뉴 추가"
        }

        saveButton.setOnClickListener {
            // 입력된 정보를 가져와서 새로운 MenuData 객체를 생성합니다.
            val name = editMenuName.text.toString()
            val description = editMenuDescription.text.toString()
            val price = editMenuPrice.text.toString().toIntOrNull() ?: 0
            val ice = checkBoxIce.isChecked
            val extraOption = editExtraOption.text.toString()

            val newMenuData = MenuData(name, description, R.drawable.coffee_ex, price, ice, extraOption)

            // 결과를 이전 화면으로 반환합니다.
            val resultIntent = Intent()
            resultIntent.putExtra("newMenuData", newMenuData)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun displayMenuData(menuData: MenuData) {
        editMenuName.setText(menuData.name)
        editMenuDescription.setText(menuData.description)
        editMenuPrice.setText(menuData.price.toString())
        checkBoxIce.isChecked = menuData.ice
        editExtraOption.setText(menuData.extraOption)
    }
}
