package com.javimartd.theguardian.v2.di

import android.content.Context
import androidx.room.Room
import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.local.db.AppDatabase
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.data.datasources.remote.common.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        private const val DATA_BASE = "the_guardian_db"
    }

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
    fun providesCacheDataSource(): NewsCacheDataSource {
        return NewsCacheDataSourceImpl()
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(appDatabase: AppDatabase): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun providesErrorHandler(): ErrorHandler {
        return RemoteErrorHandlerImpl()
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATA_BASE
        ).build()
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