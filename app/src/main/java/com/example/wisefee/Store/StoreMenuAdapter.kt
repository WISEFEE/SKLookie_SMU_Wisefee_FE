import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.wisefee.R
import java.io.Serializable

class StoreMenuAdapter(context: Context, private var data: Array<MenuData>) :
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

    fun updateData(newData: Array<MenuData>) {
        data = newData
        notifyDataSetChanged() // 어댑터에게 데이터 변경 사실을 알려줍니다.
    }
}

data class MenuData(var name: String, var description: String, var imageResId: Int, var price: Int, var ice: Boolean, var extraOption: String) :
    Serializable
