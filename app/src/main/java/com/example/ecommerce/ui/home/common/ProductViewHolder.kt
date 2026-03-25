package com.example.ecommerce.ui.home.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.ecommerce.R
import com.example.ecommerce.data.Product
import com.example.ecommerce.databinding.ViewProductBinding
import com.example.ecommerce.ui.LIST_TO_DETAIL_TRANSITION_ELEMENT

interface ProductDelegate {
    fun onFavorite(product: Product)
    fun onDetail(view: View, product: Product)
}

class ProductViewHolder(
    private val binding: ViewProductBinding,
    private val delegate: ProductDelegate
): RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product?) {
        binding.apply {
            root.transitionName = LIST_TO_DETAIL_TRANSITION_ELEMENT

            binding.thumbnailImageView.load(
                product?.thumbnail
            ) {
                crossfade(true)
                placeholder(null)
            }

            favoriteButton.isFavorite = product?.isFavorite ?: false
            nameTextView.text = product?.title
            priceTextView.text = "$${product?.price ?: 0}"

            favoriteButton.setOnClickListener {
                if (product != null) {
                    delegate.onFavorite(product)
                }
            }

            root.setOnClickListener {
                if (product != null) {
                    delegate.onDetail(root, product)
                }
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup, delegate: ProductDelegate): ProductViewHolder {
            val binding = ViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ProductViewHolder(binding, delegate)
        }
    }

}