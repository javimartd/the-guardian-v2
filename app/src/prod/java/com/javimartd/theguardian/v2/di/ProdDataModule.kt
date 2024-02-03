package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.datasources.DiskDataSource
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.cache.CacheDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.disk.db.AppDatabase
import com.javimartd.theguardian.v2.data.datasources.disk.db.news.DiskDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.data.datasources.remote.common.RemoteErrorHandlerImpl
import com.javimartd.theguardian.v2.data.datasources.remote.news.NewsApiService
import com.javimartd.theguardian.v2.data.datasources.remote.news.mapper.NewsRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProdDataModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        newsApiService: NewsApiService,
        mapper: NewsRemoteMapper,
        errorHandler: ErrorHandler
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            newsApiService,
            mapper,
            errorHandler
        )
    }

    @Provides
    @Singleton
    fun providesCacheDataSource(): CacheDataSource {
        return CacheDataSourceImpl()
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(appDatabase: AppDatabase): DiskDataSource {
        return DiskDataSourceImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun providesErrorHandler(): ErrorHandler {
        return RemoteErrorHandlerImpl()
    }
}