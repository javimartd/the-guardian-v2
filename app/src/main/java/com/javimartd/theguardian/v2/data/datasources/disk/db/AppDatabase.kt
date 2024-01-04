package com.javimartd.theguardian.v2.data.datasources.disk.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javimartd.theguardian.v2.data.datasources.disk.db.news.NewsDao
import com.javimartd.theguardian.v2.data.datasources.disk.db.news.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}