package com.example.ecommerce.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.local.ProductDao
import com.example.ecommerce.data.mappers.toProductEntity
import com.example.ecommerce.data.remote.ProductsRemoteDataSource
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator (
    private val productDao: ProductDao,
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val pageLimit: Int
): RemoteMediator<Int, Product>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = productDao.getLastProductId() ?:
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    lastItem
                }
            }

            val response = productsRemoteDataSource.getProducts(pageLimit, loadKey)

            val products = response.products.map { it.toProductEntity() }

            val clearProducts = loadType == LoadType.REFRESH
            val endOfPaginationReached = products.last().id == response.total.toLong()

            productDao.refreshProducts(products, clearProducts)

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

}