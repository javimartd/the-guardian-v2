package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import javax.inject.Inject

class NewsFakeRemoteDataSourceImpl @Inject constructor(): NewsRemoteDataSource {
    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsData>> {
        return Result.success(emptyList())
    }

    override suspend fun getSections(): Result<List<SectionData>> {
        return Result.success(emptyList())
    }
}