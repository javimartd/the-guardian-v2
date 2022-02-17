package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.coroutines.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.data.coroutines.DispatcherProvider
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSource
import com.javimartd.theguardian.v2.data.state.Resource
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): Repository {

    companion object {
        const val SHOW_FIELDS_LEVEL = "all"
    }

    override suspend fun getNews(sectionId: String): Resource<List<RawNews>> {
        return withContext(dispatchers.io()) {
            remoteDataSource.getNews(sectionId)
        }
    }

    override suspend fun getSections(): Resource<List<RawSection>> {
        return withContext(dispatchers.io()) {
            val sections = localDataSource.getSections()
            if (sections.isNullOrEmpty()) {
                val response =  remoteDataSource.getSections()
                if (response is Resource.Success) {
                    localDataSource.saveSections(response.data)
                }
                response
            } else {
                Resource.Success(sections)
            }
        }
    }
}