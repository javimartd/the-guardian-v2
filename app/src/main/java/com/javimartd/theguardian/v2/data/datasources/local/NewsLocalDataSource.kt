package com.javimartd.theguardian.v2.data.datasources.local

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    fun getNews(): Flow<List<NewsEntity>>
    fun insertAll(entities: List<NewsEntity>)
    fun removeAll()
}