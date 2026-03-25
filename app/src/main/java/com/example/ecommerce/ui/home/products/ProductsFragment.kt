package com.example.ecommerce.ui.home.products

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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.data.Product
import com.example.ecommerce.databinding.FragmentProductsBinding
import com.example.ecommerce.ui.LIST_TO_DETAIL_TRANSITION_ELEMENT
import com.example.ecommerce.ui.PRODUCT_ID
import com.example.ecommerce.ui.home.common.ProductDelegate
import com.example.ecommerce.ui.home.common.SpacerItemDecoration
import com.example.ecommerce.ui.product_details.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class ProductInfoType (val titleId: Int, val descriptionId: Int) {

    ERROR (R.string.whoops, R.string.generic_error_message),
    EMPTY(R.string.no_products, R.string.no_products_message)

}

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products), ProductDelegate {

    private val viewModel: ProductsViewModel by viewModels()

    private lateinit var binding: FragmentProductsBinding

    private lateinit var adapter: ProductsAdapter

    private val spacing = 8

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
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

    private fun setupRecycler() {
        adapter = ProductsAdapter(this)

        val combinedAdapter = adapter.withLoadStateFooter(
            footer = ProductsLoadStateAdapter()
        )

        val gridLayoutManager = GridLayoutManager(context, 2)
            .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position >= adapter.itemCount) {
                            spanCount
                        } else {
                            1
                        }
                    }
                }
            }

        binding.productsRecyclerView.layoutManager = gridLayoutManager
        binding.productsRecyclerView.adapter = combinedAdapter

        val spacingPx = (spacing * resources.displayMetrics.density).toInt()

        binding
            .productsRecyclerView
            .addItemDecoration(SpacerItemDecoration(2, spacingPx))

        binding.swipe.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.products.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }

                launch {
                    adapter.loadStateFlow.collect { loadState ->
                        manageState(loadState)
                    }
                }
            }
        }
    }

    private fun manageState(state: CombinedLoadStates) {
        val isListEmpty = adapter.itemCount == 0

        binding.progressIndicator.isVisible =
            isListEmpty && state.refresh is LoadState.Loading

        binding.swipe.isVisible = !isListEmpty

        val refreshStartedByUser = binding.swipe.isRefreshing
        val isCompleteRefresh = !isListEmpty && state.refresh is LoadState.Loading

        binding.swipe.isRefreshing = refreshStartedByUser && isCompleteRefresh

        if (state.refresh is LoadState.Error && isListEmpty) {
            displayInfoView(ProductInfoType.ERROR)
        } else if (isListEmpty && state.isIdle) {
            displayInfoView(ProductInfoType.EMPTY)
        } else {
            binding.infoView.isVisible = false
        }
    }

    private fun displayInfoView(type: ProductInfoType) {
        binding.infoView.apply {
            title = resources.getString(type.titleId)
            description = resources.getString(type.descriptionId)
        }

        binding.infoView.isVisible = true

        binding.infoView.setOnActionClickListener {
            adapter.refresh()
        }
    }

}




