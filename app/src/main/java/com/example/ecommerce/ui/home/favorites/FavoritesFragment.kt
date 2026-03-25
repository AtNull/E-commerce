package com.example.ecommerce.ui.home.favorites

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.data.Product
import com.example.ecommerce.databinding.FragmentFavoritesBinding
import com.example.ecommerce.ui.LIST_TO_DETAIL_TRANSITION_ELEMENT
import com.example.ecommerce.ui.PRODUCT_ID
import com.example.ecommerce.ui.home.common.ProductDelegate
import com.example.ecommerce.ui.home.common.SpacerItemDecoration
import com.example.ecommerce.ui.product_details.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), ProductDelegate {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var recyclerAdapter: FavoritesAdapter

    private val spacing = 8

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    override fun onFavorite(product: Product) {
        viewModel.toggleFavorite(product)
    }

    override fun onDetail(view: View, product: Product) {
        val options = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            LIST_TO_DETAIL_TRANSITION_ELEMENT
        )

        val intent = Intent(activity, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_ID, product.id)

        activity?.startActivity(intent, options.toBundle())
    }

    private fun setupRecyclerView() {
        val spacingPx = (spacing * resources.displayMetrics.density).toInt()

        binding.favoritesRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.favoritesRecycler.addItemDecoration(
            SpacerItemDecoration(2, spacingPx)
        )

        recyclerAdapter = FavoritesAdapter(this)
        binding.favoritesRecycler.adapter = recyclerAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteProductsUiState.collect { state ->
                    manageState(state)
                }
            }
        }
    }

    private fun manageState(state: FavoritesUiState) {
        when (state) {
            is FavoritesUiState.Loading -> {
                binding.progressIndicator.isVisible = true
                binding.infoView.isVisible = false
                binding.favoritesRecycler.isVisible = false
            }

            is FavoritesUiState.Success -> {
                binding.progressIndicator.isVisible = false
                binding.infoView.isVisible = state.products.isEmpty()
                binding.favoritesRecycler.isVisible = state.products.isNotEmpty()

                recyclerAdapter.submitList(state.products)
            }
        }
    }

}

