package com.javimartd.theguardian.v2.data.remote

import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.coroutines.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.data.coroutines.DispatcherProvider
import com.javimartd.theguardian.v2.data.remote.model.STATUS_OK
import com.javimartd.theguardian.v2.data.NewsDataSource
import com.javimartd.theguardian.v2.ui.mapper.mapToPresentation
import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.model.Section
import kotlinx.coroutines.withContext

class NewsRemoteDataSource(
    private val apiService: ApiService,
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
): NewsDataSource {

    companion object {
        const val SHOW_FIELDS_LEVEL = "all"
    }

    override suspend fun getNews(sectionId: String): List<News> {
        return withContext(dispatchers.io()) {
            val response = apiService.getNews(
                SHOW_FIELDS_LEVEL,
                sectionId,
                BuildConfig.THE_GUARDIAN_API_KEY
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.newsResponse?.status == STATUS_OK) {
                    body.newsResponse.mapToPresentation()
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getSections(): List<Section> {
        return withContext(dispatchers.io()) {
            val response = apiService.getSections(BuildConfig.THE_GUARDIAN_API_KEY)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.sectionsResponse?.status == STATUS_OK) {
                    body.sectionsResponse.mapToPresentation()
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }
}