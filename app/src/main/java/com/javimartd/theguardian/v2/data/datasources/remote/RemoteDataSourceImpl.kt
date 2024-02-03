package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.common.safeApiCall
import com.javimartd.theguardian.v2.data.datasources.remote.news.NewsApiService
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.STATUS_OK
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val mapper: RemoteMapper<NewsRemoteRaw, List<NewsData>>,
    private val errorHandler: ErrorHandler
): RemoteDataSource {

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

    override fun getSections(): Flow<List<SectionData>> = flow {
        val result = safeApiCall { newsApiService.getSections() }
        emit(
            result.fold(
                onSuccess = {
                    if (it.sectionsResponse.status == STATUS_OK) {
                           it.sectionsResponse.results?.map { sectionRaw ->
                                SectionData(
                                    id = sectionRaw.id,
                                    webTitle = sectionRaw.webTitle,
                                    webUrl = sectionRaw.webUrl
                                )
                            } ?: emptyList()
                    } else {
                        emptyList()
                    }
                },
                onFailure = { emptyList() }
            )
        )
    }.catch {
        emit(emptyList())
    }
}