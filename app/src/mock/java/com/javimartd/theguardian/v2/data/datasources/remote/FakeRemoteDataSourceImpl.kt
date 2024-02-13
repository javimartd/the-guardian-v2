package com.javimartd.theguardian.v2.data.datasources.remote

import com.google.gson.Gson
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.readJsonFromAssets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

    override fun getSections(): Flow<List<SectionData>> {
        return flowOf(
            listOf(
                SectionData(
                    id = "artanddesign",
                    webTitle = "Art and design",
                    webUrl = "https://www.theguardian.com/artanddesign"
                ),
                SectionData(
                    id = "animals-farmed",
                    webTitle = "Animals farmed",
                    webUrl = "https://www.theguardian.com/animals-farmed"
                ),
                SectionData(
                    id = "books",
                    webTitle = "Books",
                    webUrl = "https://www.theguardian.com/books"
                ),
                SectionData(
                    id = "business",
                    webTitle = "Business",
                    webUrl = "https://www.theguardian.com/business"
                ),
                SectionData(
                    id = "cities",
                    webTitle = "Cities",
                    webUrl = "https://www.theguardian.com/cities"
                ),
                SectionData(
                    id = "culture",
                    webTitle = "Culture",
                    webUrl = "https://www.theguardian.com/culture"
                )
            )
        )
    }
}