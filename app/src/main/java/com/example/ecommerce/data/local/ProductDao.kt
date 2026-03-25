package com.example.ecommerce.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.ecommerce.data.Product
import com.example.ecommerce.data.mappers.toFavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertProducts(product: List<ProductEntity>)

    @Query("SELECT *, " +
            "(SELECT COUNT(*) FROM favorite_product WHERE favorite_product.id = products.id) > 0 AS isFavorite " +
            "FROM products")
    fun pagingSource(): PagingSource<Int, Product>

    @Query("SELECT id FROM products ORDER BY id DESC LIMIT 1")
    suspend fun getLastProductId(): Long?

    @Query("SELECT *, " +
            "(SELECT COUNT(*) FROM favorite_product WHERE favorite_product.id = products.id) > 0 AS isFavorite " +
            "FROM products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Long): Product?

    @Query("DELETE FROM products")
    suspend fun clearProducts()

    @Query("SELECT *," +
            "1 AS isFavorite " +
            "FROM favorite_product WHERE id = :id LIMIT 1")
    suspend fun getFavoriteById(id: Long): Product?

    @Query("SELECT * FROM favorite_product")
    fun getFavorites(): Flow<List<FavoriteProductEntity>>
    @Query("SELECT 1 FROM favorite_product WHERE id = :id")
    suspend fun productIsFavorite(id: Long): Boolean

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(product: FavoriteProductEntity)

    @Delete
    suspend fun deleteFavorite(product: FavoriteProductEntity)

    @Transaction
    suspend fun toggleFavorite(product: ProductEntity) {
        val favoriteProduct = product.toFavoriteProductEntity()

        if (productIsFavorite(product.id)) {
            deleteFavorite(favoriteProduct)
        } else {
            insertFavorite(favoriteProduct)
        }
    }

    @Transaction
    suspend fun refreshProducts(products: List<ProductEntity>, clear: Boolean) {
        if (clear) {
            clearProducts()
        }

        insertProducts(products)
    }

}