package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.ApiKeyInterceptor
import com.javimartd.theguardian.v2.data.datasources.remote.ApiService
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteErrorHandlerImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Manual dependency injection
 * https://developer.android.com/training/dependency-injection/manual
 */
class ServiceLocator {

    private val baseUrl = "https://content.guardianapis.com"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient()
            .newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val errorHandler = RemoteErrorHandlerImpl()
    private val apiService = retrofit.create(ApiService::class.java)
    private val localDataSource = LocalDataSourceImpl()
    private val remoteDataSource = RemoteDataSourceImpl(
        apiService,
        errorHandler
    )
    val repository = RepositoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )
}