package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.data.state.Result

interface NewsRepository {
    companion object {
        const val SHOW_FIELDS_LEVEL = "all"
        const val WORLD_NEWS_SECTION_ID = "world"
    }
    suspend fun getNews(sectionName: String = ""): Result<List<NewsRaw>>
    suspend fun getSections(): Result<List<SectionRaw>>
}