package com.example.wisefee.Subscribe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wisefee.R
import com.example.wisefee.databinding.StoreItemLayoutBinding

class StoreAdapter(private val storeLocations: List<Store>) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(private val binding: StoreItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.store = store       //store_item_layout.xml에 있는 store
            binding.executePendingBindings()   // 변경된 데이터와 바인딩된 뷰 간의 연결을 즉시 업데이트
        }

        companion object {
            fun create(parent: ViewGroup): StoreViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = StoreItemLayoutBinding.inflate(inflater, parent, false)
                return StoreViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val storeLocation = storeLocations[position]
        holder.bind(storeLocation)
    }

    override fun getItemCount(): Int {
        return storeLocations.size
    }
}
