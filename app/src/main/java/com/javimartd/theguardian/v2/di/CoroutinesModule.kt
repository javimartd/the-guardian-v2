package com.javimartd.theguardian.v2.di

import com.javimartd.theguardian.v2.data.datasources.coroutines.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.data.datasources.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {

    @Provides
    @Singleton
    fun providesDispatcher(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}