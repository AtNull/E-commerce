package com.example.ecommerce.ui.home.products

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ProductsLoadStateAdapter: LoadStateAdapter<ProductsLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ProductsLoadStateViewHolder {
        return ProductsLoadStateViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: ProductsLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind()
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return ProductsLoadStateViewHolder.VIEW_TYPE
    }

}