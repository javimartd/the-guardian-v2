package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSource
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
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}