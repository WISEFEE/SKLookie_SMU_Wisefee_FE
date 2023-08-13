package com.example.wisefee.Menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.Cart.Product
import com.example.wisefee.databinding.MenuItemLayoutBinding


class MenuAdapter(private val menuList: List<Product>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuName = menuList[position]
        holder.bind(menuName)
    }
    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MenuViewHolder(private val binding: MenuItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.menuNameTextView.text = product.name;
            binding.menuPriceTextView.text = product.price.toString();
         }
    }
}