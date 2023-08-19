package com.example.wisefee.Menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.databinding.MenuItemLayoutBinding


class MenuAdapter(private var products: List<Product>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(private val binding: MenuItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.menuNameTextView.text = product.productName;
            binding.menuPriceTextView.text = product.productPrice.toString();
        }
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
}


