package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.data.datasources.local.db.AppDatabase
import com.javimartd.theguardian.v2.data.mapper.toData
import com.javimartd.theguardian.v2.data.mapper.toDomain
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
): NewsLocalDataSource {

    override fun getNews(): Flow<List<NewsEntity>> {
        return appDatabase.newsDao().getAll().map { news -> news.map { it.toDomain() } }
    }

    override fun removeAll() {
        appDatabase.newsDao().removeAll()
    }

    override fun insertAll(entities: List<NewsEntity>) {
        appDatabase.newsDao().insertAll(entities.map { it.toData() })
    }
}