package com.javimartd.theguardian.v2.data.datasources.remote

import com.google.gson.Gson
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.readJsonFromAssets
import javax.inject.Inject

class FakeRemoteDataSourceImpl @Inject constructor(
    private val mapper: RemoteMapper<NewsRemoteRaw, List<NewsData>>,
): RemoteDataSource {
    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsData>> {
        val jsonString = readJsonFromAssets("news.json")
        val json = Gson().fromJson(jsonString, NewsRemoteRaw::class.java)
        return Result.success(mapper.mapFromRemote(json))
    }

    override suspend fun getSections(): Result<List<SectionData>> {
        return Result.success(emptyList())
    }
}