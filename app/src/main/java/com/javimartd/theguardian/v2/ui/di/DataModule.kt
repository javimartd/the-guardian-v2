package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.data.datasources.remote.common.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    private val baseUrl = "https://content.guardianapis.com"

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        newsApiService: NewsApiService,
        errorHandler: ErrorHandler
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(
            newsApiService,
            errorHandler
        )
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(): NewsCacheDataSource {
        return NewsCacheDataSourceImpl()
    }

    @Provides
    @Singleton
    fun providesErrorHandler(): ErrorHandler {
        return RemoteErrorHandlerImpl()
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient()
                .newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    }
                )
                .addInterceptor(ApiKeyInterceptor())
                .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}