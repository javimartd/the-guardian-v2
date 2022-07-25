package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.NewsRepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Result
import retrofit2.HttpException
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorHandler: ErrorHandler
): NewsRemoteDataSource {

    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsRaw>> {
        return try {
            val response = apiService.getNews(
                showFieldsAll,
                sectionId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.newsResponse?.status == STATUS_OK) {
                    Result.Success(body.newsResponse.results ?: emptyList())
                } else {
                    Result.Error(ErrorTypes.RemoteErrors.ApiStatus)
                }
            } else {
                Result.Error(errorHandler.getError(HttpException(response)))
            }
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }

    override suspend fun getSections(): Result<List<SectionRaw>> {
        return try {
            val response = apiService.getSections()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.sectionsResponse?.status == STATUS_OK) {
                    Result.Success(body.sectionsResponse.results ?: emptyList())
                } else {
                    Result.Error(ErrorTypes.RemoteErrors.ApiStatus)
                }
            } else {
                Result.Error(errorHandler.getError(HttpException(response)))
            }
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}