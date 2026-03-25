package com.example.ecommerce.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FavoritesUiState {

    object Loading : FavoritesUiState()
    data class Success(val products: List<Product>) : FavoritesUiState()

}

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    val favoriteProductsUiState = repository
        .getFavorites()
        .map {
            FavoritesUiState.Success(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FavoritesUiState.Loading
        )

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.toggleFavorite(product)
        }
    }

}
