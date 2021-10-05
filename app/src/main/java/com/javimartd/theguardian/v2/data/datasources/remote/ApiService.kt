package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.datasources.model.NewsResponse
import com.javimartd.theguardian.v2.data.datasources.model.SectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search")
    suspend fun getNews(
        @Query("show-fields") showFields: String,
        @Query("section") section: String
    ): Response<NewsResponse>

    @GET("/sections")
    suspend fun getSections(): Response<SectionsResponse>
}