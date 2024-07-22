package com.javimartd.theguardian.v2.domain

import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<News>>
    fun getSections(): Flow<List<SectionData>>
    suspend fun longTaskInBackground(): List<String>
}