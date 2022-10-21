package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.coroutines.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.data.coroutines.DispatcherProvider
import com.javimartd.theguardian.v2.data.datasources.cache.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRemoteDataSource
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val remoteDataSource: NewsRemoteDataSource,
    private val cacheDataSource: NewsCacheDataSource,
): NewsRepository {

    override suspend fun getNews(
        showFieldsAll: String,
        sectionId: String
    ): Result<List<NewsEntity>> {
        return withContext(dispatchers.io()) {
            remoteDataSource.getNews(
                showFieldsAll,
                sectionId
            )
        }
    }

    override suspend fun getSections(): Result<List<SectionEntity>> {
        return withContext(dispatchers.io()) {
            val sections = cacheDataSource.getSections()
            if (sections.isEmpty()) {
                val response =  remoteDataSource.getSections()
                response.onSuccess { cacheDataSource.saveSections(it) }
                response
            } else {
                Result.success(sections)
            }
        }
    }
}