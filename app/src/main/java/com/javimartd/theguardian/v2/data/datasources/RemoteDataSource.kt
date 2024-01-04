package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData

interface RemoteDataSource {
    suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsData>>
    suspend fun getSections(): Result<List<SectionData>>
}