package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.state.Result

interface NewsRemoteDataSource {
    suspend fun getNews(
        showFieldsAll: String = "all",
        sectionId: String
    ): Result<List<NewsRaw>>
    suspend fun getSections(): Result<List<SectionRaw>>
}