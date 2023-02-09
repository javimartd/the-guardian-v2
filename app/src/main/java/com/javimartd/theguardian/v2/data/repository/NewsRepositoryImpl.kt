package com.javimartd.theguardian.v2.data.repository

import com.javimartd.theguardian.v2.features.common.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.features.common.DispatcherProvider
import com.javimartd.theguardian.v2.data.datasources.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val remoteDataSource: NewsRemoteDataSource,
    private val cacheDataSource: NewsCacheDataSource,
    private val localDataStore: NewsLocalDataSource
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