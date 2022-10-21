package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.data.NewsRepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class NewsViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesRepository(
        remoteDataSource: NewsRemoteDataSource,
        cacheDataSource: NewsCacheDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource
        )
    }
}