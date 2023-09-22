import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.wisefee.R

class StoreMenuAdapter(context: Context, private val data: Array<MenuData>) :
    ArrayAdapter<MenuData>(context, R.layout.store_menu_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.store_menu_item,
            parent,
            false
        )

        val menuItem = data[position]
        val menuItemImage = itemView.findViewById<ImageView>(R.id.menuItemImage)
        val menuItemName = itemView.findViewById<TextView>(R.id.menuItemName)
        val menuItemDescription = itemView.findViewById<TextView>(R.id.menuItemDescription)

        // 데이터 설정
        menuItemImage.setImageResource(menuItem.imageResId) // 이미지 리소스 설정
        menuItemName.text = menuItem.name
        menuItemDescription.text = menuItem.description

        return itemView
    }
}

data class MenuData(val name: String, val description: String, val imageResId: Int)