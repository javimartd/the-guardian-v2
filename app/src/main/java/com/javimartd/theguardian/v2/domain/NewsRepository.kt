package com.javimartd.theguardian.v2.domain

import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section

interface NewsRepository {
    suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<News>>
    suspend fun getSections(): Result<List<Section>>
}