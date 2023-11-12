package com.example.wisefee.Menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.databinding.MenuItemLayoutBinding
import com.example.wisefee.dto.Product


//private val onProductClickListener: (product: Product) -> Unit
class MenuAdapter(private var products: List<Product>, private val productClickListener: OnProductClickListener)
    : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(products[position])
    }
    override fun getItemCount(): Int {
        return products.size
    }


    inner class MenuViewHolder(private val binding: MenuItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.menuNameTextView.text = product.productName;
            //binding.menuPriceTextView.text = product.productPrice.toString();
            val productPrice = product.productPrice.toString()
            binding.menuPriceTextView.text = "${productPrice.replace(" ", "")}원"


            // 메뉴 아이템 클릭 시
            binding.root.setOnClickListener {
                productClickListener.onProductClick(product) // 클릭 이벤트 처리
            }
        }
    }
}