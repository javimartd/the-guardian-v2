package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.Resource
import kotlinx.coroutines.delay

class RemoteDataSourceFakeImpl(
    private val apiService: ApiService,
    private val errorHandler: ErrorHandler
): RemoteDataSource {


    override suspend fun getNews(sectionId: String): Resource<List<RawNews>> {
        delay(20000)
        return Resource.Success(emptyList())
    }

    override suspend fun getSections(): Resource<List<RawSection>> {
        delay(20000)
        return Resource.Success(emptyList())
    }
}