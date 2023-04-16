package com.javimartd.theguardian.v2.data.datasources.remote.news

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteMapper
import com.javimartd.theguardian.v2.data.datasources.remote.common.safeApiCall
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.STATUS_OK
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val mapper: RemoteMapper<NewsRemoteRaw, List<NewsData>>,
    private val errorHandler: ErrorHandler
): NewsRemoteDataSource {

    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsData>> {
        val result = safeApiCall {
            newsApiService.getNews(
                showFieldsAll,
                sectionId
            )
        }
        return result.fold(
            onSuccess = { response ->
                if (response.newsResponse?.status == STATUS_OK) {
                    Result.success(mapper.mapFromRemote(response))
                } else {
                    Result.failure(ErrorTypes.RemoteErrors.ApiStatus)
                }
            },
            onFailure = {
                Result.failure(errorHandler.getError(it))
            }
        )
    }

    override suspend fun getSections(): Result<List<SectionData>> {
        val result = safeApiCall { newsApiService.getSections() }

        return result.fold(
            onSuccess = { response ->
                if (response.sectionsResponse.status == STATUS_OK) {
                    Result.success(
                        response.sectionsResponse.results?.map {
                            SectionData(
                                id = it.id,
                                webTitle = it.webTitle,
                                webUrl = it.webUrl
                            )
                        } ?: emptyList()
                    )
                } else {
                    Result.failure(ErrorTypes.RemoteErrors.ApiStatus)
                }
            },
            onFailure = {
                Result.failure(errorHandler.getError(it))
            }
        )
    }
}