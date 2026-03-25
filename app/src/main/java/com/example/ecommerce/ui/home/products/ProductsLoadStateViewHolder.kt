package com.example.ecommerce.ui.home.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ViewProductLoaderBinding

class ProductsLoadStateViewHolder(private val binding: ViewProductLoaderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding
            .progressIndicator
            .animate()
            .start()
    }

    companion object {
        const val VIEW_TYPE = 1337

        fun create(parent: ViewGroup): ProductsLoadStateViewHolder {
            val binding = ViewProductLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ProductsLoadStateViewHolder(binding)
        }
    }

}