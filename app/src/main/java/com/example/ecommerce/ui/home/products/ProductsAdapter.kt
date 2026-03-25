package com.example.ecommerce.ui.home.products

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.ecommerce.data.Product
import com.example.ecommerce.ui.home.common.ProductDelegate
import com.example.ecommerce.ui.home.common.ProductViewHolder

class ProductsAdapter(private val delegate: ProductDelegate): PagingDataAdapter<Product, ProductViewHolder>(Comparator) {

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

            override fun getChangePayload(oldItem: Product, newItem: Product): Any {
                return Any()
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
