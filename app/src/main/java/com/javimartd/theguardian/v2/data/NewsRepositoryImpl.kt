package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.coroutines.DefaultDispatcherProvider
import com.javimartd.theguardian.v2.data.coroutines.DispatcherProvider
import com.javimartd.theguardian.v2.data.datasources.local.NewsLocalDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.data.state.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource,
): NewsRepository {

    override suspend fun getNews(sectionName: String): Result<List<NewsRaw>> {
        return withContext(dispatchers.io()) {
            val sections = localDataSource.getSections()
            val sectionId = sections.singleOrNull {
                sectionName == it.webTitle
            }?.id ?: NewsRepository.WORLD_NEWS_SECTION_ID
            remoteDataSource.getNews(
                NewsRepository.SHOW_FIELDS_LEVEL,
                sectionId
            )
        }
    }

    override suspend fun getSections(): Result<List<SectionRaw>> {
        return withContext(dispatchers.io()) {
            val sections = localDataSource.getSections()
            if (sections.isEmpty()) {
                val response =  remoteDataSource.getSections()
                if (response is Result.Success) {
                    localDataSource.saveSections(response.data)
                }
                response
            } else {
                Result.Success(sections)
            }
        }
    }
}