package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import javax.inject.Inject

class NewsRemoteFakeDataSourceImpl @Inject constructor(
    private val apiService: NewsApiService
): NewsRemoteDataSource {
    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsEntity>> {
        return Result.success(emptyList())
    }

    override suspend fun getSections(): Result<List<SectionEntity>> {
        return Result.success(emptyList())
    }

}