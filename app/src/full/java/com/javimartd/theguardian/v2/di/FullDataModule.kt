package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.local.news.NewsLocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.local.AppDatabase
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.data.datasources.remote.common.RemoteErrorHandlerImpl
import com.javimartd.theguardian.v2.data.datasources.remote.news.NewsApiService
import com.javimartd.theguardian.v2.data.datasources.remote.news.NewsRemoteDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.news.mapper.NewsRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FullDataModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        newsApiService: NewsApiService,
        mapper: NewsRemoteMapper,
        errorHandler: ErrorHandler
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(
            newsApiService,
            mapper,
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
}