package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.data.NewsRepository
import com.javimartd.theguardian.v2.data.NewsRepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSource
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
        localDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}