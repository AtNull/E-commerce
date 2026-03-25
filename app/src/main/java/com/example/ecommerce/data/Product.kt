package com.example.ecommerce.data

data class Product (
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val thumbnail: String,
    val isFavorite: Boolean
)