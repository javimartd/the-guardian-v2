package com.javimartd.theguardian.v2.data.repository

import com.javimartd.theguardian.v2.data.datasources.NewsCacheDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.repository.news.mapper.toDomain
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.features.common.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.features.common.DispatcherProvider
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
    ): Result<List<News>> {
        return withContext(dispatchers.io()) {
            val data = remoteDataSource.getNews(
                showFieldsAll,
                sectionId
            )
            data.fold(
                onSuccess = { Result.success(it.map { newsData -> newsData.toDomain() }) },
                onFailure = { Result.failure(it) }
            )
        }
    }

    override suspend fun getSections(): Result<List<Section>> {
        return withContext(dispatchers.io()) {
            val sections = cacheDataSource.getSections()
            if (sections.isEmpty()) {
                val response =  remoteDataSource.getSections()
                response.fold(
                    onSuccess = {
                        cacheDataSource.saveSections(it)
                        Result.success(it.map { sectionData -> sectionData.toDomain() })
                    },
                    onFailure = { Result.failure(it) }
                )
            } else {
                Result.success(sections.map { it.toDomain() })
            }
        }
    }
}