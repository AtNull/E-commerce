package com.example.ecommerce.ui.home.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ecommerce.data.Product
import com.example.ecommerce.ui.home.common.ProductDelegate
import com.example.ecommerce.ui.home.common.ProductViewHolder

class FavoritesAdapter(private val delegate: ProductDelegate): ListAdapter<Product, ProductViewHolder>(Comparator) {

    companion object{
        private val Comparator = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder.create(parent, delegate)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        holder.bind(item)
    }

}
