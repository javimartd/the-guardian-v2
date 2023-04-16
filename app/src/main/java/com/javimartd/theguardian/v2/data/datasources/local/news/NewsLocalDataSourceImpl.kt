package com.javimartd.theguardian.v2.data.datasources.local.news

import com.javimartd.theguardian.v2.data.datasources.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.AppDatabase
import com.javimartd.theguardian.v2.data.repository.news.mapper.toData
import com.javimartd.theguardian.v2.data.repository.news.mapper.toDomain
import com.javimartd.theguardian.v2.domain.news.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
): NewsLocalDataSource {

    override fun getNews(): Flow<List<News>> {
        return appDatabase.newsDao().getAll().map { news -> news.map { it.toDomain() } }
    }

    override suspend fun removeAll() {
        appDatabase.newsDao().removeAll()
    }

    override suspend fun insertAll(entities: List<News>) {
        appDatabase.newsDao().insertAll(entities.map { it.toData() })
    }
}