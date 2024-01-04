package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.domain.news.model.News
import kotlinx.coroutines.flow.Flow

interface DiskDataSource {
    fun getNews(): Flow<List<News>>
    suspend fun insertAll(entities: List<News>)
    suspend fun removeAll()
}