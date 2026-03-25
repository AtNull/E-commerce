package com.example.ecommerce.data.mappers

import com.example.ecommerce.data.Product
import com.example.ecommerce.data.local.FavoriteProductEntity
import com.example.ecommerce.data.local.ProductEntity
import com.example.ecommerce.data.remote.ProductResponse

fun Product.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    thumbnail = thumbnail
)

fun ProductResponse.toProductEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = images.firstOrNull() ?: "",
    thumbnail = thumbnail,
)

fun ProductEntity.toFavoriteProductEntity() = FavoriteProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    thumbnail = thumbnail
)

fun FavoriteProductEntity.toProduct(isFavorite: Boolean) = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    thumbnail = thumbnail,
    isFavorite = isFavorite
)
