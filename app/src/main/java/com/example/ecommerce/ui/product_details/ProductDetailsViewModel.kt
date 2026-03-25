package com.example.ecommerce.ui.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.repository.ProductRepository
import com.example.ecommerce.ui.PRODUCT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductDetailsUiState {

    object Loading : ProductDetailsUiState()
    data class Success(val product: Product) : ProductDetailsUiState()
    object NotFound: ProductDetailsUiState()

}

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _productUiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val productUiState: StateFlow<ProductDetailsUiState> = _productUiState

    init {
        val itemId = savedStateHandle.get<Long>(PRODUCT_ID) ?: 0

        viewModelScope.launch {
            val product = productRepository.getProductById(itemId)

            _productUiState.value = if (product != null) {
                ProductDetailsUiState.Success(product)
            } else {
                ProductDetailsUiState.NotFound
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _productUiState.value

        if (currentState is ProductDetailsUiState.Success) {
            val currentProduct = currentState.product

            viewModelScope.launch {
                productRepository.toggleFavorite(currentProduct)

                _productUiState.update {
                    ProductDetailsUiState.Success(
                        currentProduct.copy(
                            isFavorite = !currentProduct.isFavorite
                        )
                    )
                }
            }
        }
    }

}