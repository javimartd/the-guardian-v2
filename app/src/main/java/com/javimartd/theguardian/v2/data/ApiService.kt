package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.model.NewsResponse
import com.javimartd.theguardian.v2.data.model.SectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search")
    suspend fun getNews(
        @Query("show-fields") showFields: String,
        @Query("section") section: String,
        @Query("api-key") apiKey: String
    ): Response<NewsResponse>

    @GET("/sections")
    suspend fun getSections(
        @Query("api-key") apiKey: String
    ): Response<SectionsResponse>
}