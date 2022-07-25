package com.javimartd.theguardian.v2.data.datasources.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search")
    suspend fun getNews(
        @Query("show-fields") showFields: String,
        @Query("section") section: String
    ): Response<NewsResponseRaw>

    @GET("/sections")
    suspend fun getSections(): Response<SectionsResponseRaw>
}