package com.example.ecommerce.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Each table requires its own entity.
@Entity("products")
data class ProductEntity (
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val thumbnail: String
)

@Entity("favorite_product")
data class FavoriteProductEntity (
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val thumbnail: String
)