package com.example.ecommerce.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse (
    val products: List<ProductResponse>,
    val total: Int
)

@Serializable
data class ProductResponse (
    var id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val images: List<String>,
    val thumbnail: String
)
