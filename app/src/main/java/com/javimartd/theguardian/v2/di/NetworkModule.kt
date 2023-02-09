package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.datasources.remote.news.NewsApiService
import com.javimartd.theguardian.v2.data.datasources.remote.common.interceptor.ApiKeyInterceptor
import com.javimartd.theguardian.v2.data.datasources.remote.common.interceptor.CacheInterceptor
import com.javimartd.theguardian.v2.data.datasources.remote.common.interceptor.ResponseSourceInterceptor
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

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(ApiKeyInterceptor())
                    .addInterceptor(ResponseSourceInterceptor())
                    .addNetworkInterceptor(CacheInterceptor())
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