package com.javimartd.theguardian.v2.data.datasources

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity

interface NewsRemoteDataSource {
    suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsEntity>>
    suspend fun getSections(): Result<List<SectionEntity>>
}