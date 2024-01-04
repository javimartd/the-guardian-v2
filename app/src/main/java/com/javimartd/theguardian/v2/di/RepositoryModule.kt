package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.data.repository.NewsRepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.datasources.DiskDataSource
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.disk.preferences.PreferencesDataStoreDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesRepository(
        remoteDataSource: RemoteDataSource,
        cacheDataSource: CacheDataSource,
        diskDataSource: DiskDataSource,
        dataStore: PreferencesDataStoreDelegate
    ): NewsRepository {
        return NewsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
            diskDataStore = diskDataSource,
            dataStore = dataStore
        )
    }
}