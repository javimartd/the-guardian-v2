package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.data.NewsRepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesRepository(
        remoteDataSource: NewsRemoteDataSource,
        cacheDataSource: NewsCacheDataSource,
        localDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
            localDataStore = localDataSource
        )
    }
}