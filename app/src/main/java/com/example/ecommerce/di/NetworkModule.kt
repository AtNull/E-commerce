package com.example.ecommerce.di

import com.example.ecommerce.BuildConfig
import com.example.ecommerce.data.remote.ProductsRemoteDataSource
import com.example.ecommerce.data.repository.ProductRepository
import com.example.ecommerce.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideProductsRepository(repository: ProductRepositoryImpl): ProductRepository

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=utf-8".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRemoteDataSource(retrofit: Retrofit): ProductsRemoteDataSource {
        return retrofit.create(ProductsRemoteDataSource::class.java)
    }

    @Provides
    @Named("page_limit")
    fun providePageLimit(): Int = 50

}