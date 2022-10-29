package com.javimartd.theguardian.v2.data.datasources.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javimartd.theguardian.v2.data.datasources.local.model.NewsLocalData

@Database(entities = [NewsLocalData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}