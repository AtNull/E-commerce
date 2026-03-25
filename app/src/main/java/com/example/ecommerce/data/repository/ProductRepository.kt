package com.example.ecommerce.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.withTransaction
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.local.AppDatabase
import com.example.ecommerce.data.local.ProductDao
import com.example.ecommerce.data.local.ProductEntity
import com.example.ecommerce.data.mappers.toEntity
import com.example.ecommerce.data.mappers.toProduct
import com.example.ecommerce.data.mappers.toProductEntity
import com.example.ecommerce.data.remote.ProductResponse
import com.example.ecommerce.data.remote.ProductsRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import okio.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

interface ProductRepository {

    fun getPagedProducts(): Flow<PagingData<Product>>
    fun getFavorites(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product)
    suspend fun getProductById(id: Long): Product?

}
