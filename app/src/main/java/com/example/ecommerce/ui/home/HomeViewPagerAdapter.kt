package com.example.ecommerce.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce.ui.home.favorites.FavoritesFragment
import com.example.ecommerce.ui.home.products.ProductsFragment

class HomeViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductsFragment()
            else -> FavoritesFragment()
        }
    }

}