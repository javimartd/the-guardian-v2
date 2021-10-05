package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.model.*
import com.javimartd.theguardian.v2.data.state.Resource

class RemoteDataSourceImpl(
    private val apiService: ApiService
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
                    Resource.Error(Exception())
                }
            } else {
                Resource.Error(Exception())
            }
        } catch (e: Exception) {
            Resource.Error(e)
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
                    Resource.Error(Exception())
                }
            } else {
                Resource.Error(Exception())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}