package com.javimartd.theguardian.v2.data.datasources.remote.news

import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.common.safeApiCall
import com.javimartd.theguardian.v2.data.mapper.toDomain
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.STATUS_OK
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val errorHandler: ErrorHandler
): NewsRemoteDataSource {

    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsEntity>> {
        val result = safeApiCall {
            newsApiService.getNews(
                showFieldsAll,
                sectionId
            )
        }
        return result.fold(
            onSuccess = { response ->
                if (response.newsResponse?.status == STATUS_OK) {
                    Result.success(
                        response.newsResponse.results?.map {
                            it.toDomain()
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

    override suspend fun getSections(): Result<List<SectionEntity>> {
        val result = safeApiCall { newsApiService.getSections() }

        return result.fold(
            onSuccess = { response ->
                if (response.sectionsResponse.status == STATUS_OK) {
                    Result.success(
                        response.sectionsResponse.results?.map {
                            it.toDomain()
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