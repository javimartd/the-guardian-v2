package com.javimartd.theguardian.v2.di

import android.content.Context
import androidx.room.Room
import com.javimartd.theguardian.v2.data.datasources.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    companion object {
        private const val DATA_BASE = "the_guardian_db"
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
}