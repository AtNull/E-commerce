package com.example.ecommerce.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.local.ProductDao
import com.example.ecommerce.data.mappers.toEntity
import com.example.ecommerce.data.mappers.toProduct
import com.example.ecommerce.data.remote.ProductsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class ProductRepositoryImpl @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val productDao: ProductDao,
    @param:Named("page_limit") private val pageLimit: Int
): ProductRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageLimit,
                prefetchDistance = pageLimit / 2,
                initialLoadSize = pageLimit
            ),
            remoteMediator = ProductRemoteMediator(
                productDao,
                productsRemoteDataSource,
                pageLimit
            )
        ) {
            productDao.pagingSource()
        }
            .flow
    }

    override fun getFavorites(): Flow<List<Product>> {
        return productDao.getFavorites().map {
                productList -> productList.map {
            it.toProduct(true)
        }
        }
    }

    override suspend fun toggleFavorite(product: Product) {
        productDao.toggleFavorite(product.toEntity())
    }

    override suspend fun getProductById(id: Long): Product? {
        val product = productDao.getProductById(id)

        // products table is cached via paging and can be invalidated
        // if product is missing from products try favourite
        // in proper implementation I would fetch it from backend
        return product ?: productDao.getFavoriteById(id)
    }

}
