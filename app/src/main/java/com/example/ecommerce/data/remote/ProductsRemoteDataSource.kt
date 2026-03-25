package com.example.ecommerce.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsRemoteDataSource {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Long
    ): ProductListResponse

}