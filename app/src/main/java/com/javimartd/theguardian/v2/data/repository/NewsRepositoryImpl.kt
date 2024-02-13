package com.javimartd.theguardian.v2.data.repository

import com.javimartd.theguardian.v2.data.datasources.CacheDataSource
import com.javimartd.theguardian.v2.data.datasources.DiskDataSource
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.disk.preferences.PreferencesDataStoreDelegate
import com.javimartd.theguardian.v2.data.repository.news.mapper.toDomain
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.NewsRepository
import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.features.common.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.features.common.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource,
    private val diskDataStore: DiskDataSource,
    private val dataStore: PreferencesDataStoreDelegate
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

    override fun getSections(): Flow<List<SectionData>> {
        return remoteDataSource.getSections().flowOn(dispatchers.io())
        /*val sections = cacheDataSource.getSections()
        return if (sections.isEmpty()) {
            remoteDataSource.getSections().onEach { cacheDataSource.saveSections(it) }
        } else {
            flowOf(sections)
        }*/
    }
}