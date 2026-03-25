package com.example.ecommerce.ui.product_details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.crossfade
import com.example.ecommerce.R
import com.example.ecommerce.data.Product
import com.example.ecommerce.databinding.ActivityProductDetailsBinding
import com.example.ecommerce.ui.LIST_TO_DETAIL_TRANSITION_ELEMENT
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {

    private val viewModel: ProductDetailsViewModel by viewModels()

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setupActivityTransition()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupDismiss()

        observeViewModel()

        consumePadding()
    }

    private fun setupActivityTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        findViewById<View>(android.R.id.content).transitionName = LIST_TO_DETAIL_TRANSITION_ELEMENT

        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = resources.getColor(R.color.container, null)
            addTarget(android.R.id.content)
            duration = 300L
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            scrimColor = resources.getColor(R.color.container, null)
            addTarget(android.R.id.content)
            duration = 300L
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }

    private fun setupDismiss() {
        binding.toolbar.closeButton.setOnClickListener {
            supportFinishAfterTransition()
        }

        onBackPressedDispatcher.addCallback(this) {
            supportFinishAfterTransition()
        }
    }

    private fun observeViewModel () {
        binding.toolbar.favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productUiState.collect { state ->
                    binding.progressIndicator.isVisible = state is ProductDetailsUiState.Loading

                    binding.infoView.isVisible = state is ProductDetailsUiState.NotFound

                    binding.productDetailsView.root.isVisible = state is ProductDetailsUiState.Success
                    binding.toolbar.favoriteButton.isVisible = state is ProductDetailsUiState.Success
                    binding.productImageView.isVisible = state is ProductDetailsUiState.Success

                    if (state is ProductDetailsUiState.Success) {
                        displayProductData(state.product)
                    }
                }
            }
        }
    }

    private fun displayProductData(product: Product) {
        binding.productImageView.load(
            product.image
        ) {
            crossfade(true)
            placeholderMemoryCacheKey(product.thumbnail)
        }

        binding.productDetailsView.titleTextView.text = product.title
        binding.productDetailsView.priceTextView.text = "$${product.price}"
        binding.productDetailsView.descriptionTextView.text = product.description
        binding.toolbar.favoriteButton.isFavorite = product.isFavorite
    }

    private fun consumePadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right,0)

            insets
        }
    }

}