package com.javimartd.theguardian.v2.data.datasources.remote.news

import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsResponseRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.SectionsResponseRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/search")
    suspend fun getNews(
        @Query("show-fields") showFields: String,
        @Query("section") section: String
    ): Response<NewsResponseRaw>

    @GET("/sections")
    suspend fun getSections(): Response<SectionsResponseRaw>
}