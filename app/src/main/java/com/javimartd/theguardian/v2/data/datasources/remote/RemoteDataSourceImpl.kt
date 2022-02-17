package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.datasources.model.STATUS_OK
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Resource
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorHandler: ErrorHandler
): RemoteDataSource {

    override suspend fun getNews(sectionId: String): Resource<List<RawNews>> {
        return try {
            val response = apiService.getNews(
                RepositoryImpl.SHOW_FIELDS_LEVEL,
                sectionId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.newsResponse?.status == STATUS_OK) {
                    Resource.Success(body.newsResponse.results ?: emptyList())
                } else {
                    Resource.Error(ErrorTypes.RemoteErrors.ApiStatus)
                }
            } else {
                Resource.Error(errorHandler.getError(HttpException(response)))
            }
        } catch (throwable: Throwable) {
            Resource.Error(errorHandler.getError(throwable))
        }
    }

    override suspend fun getSections(): Resource<List<RawSection>> {
        return try {
            val response = apiService.getSections()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.sectionsResponse?.status == STATUS_OK) {
                    Resource.Success(body.sectionsResponse.results ?: emptyList())
                } else {
                    Resource.Error(ErrorTypes.RemoteErrors.ApiStatus)
                }
            } else {
                Resource.Error(errorHandler.getError(HttpException(response)))
            }
        } catch (throwable: Throwable) {
            Resource.Error(errorHandler.getError(throwable))
        }
    }
}