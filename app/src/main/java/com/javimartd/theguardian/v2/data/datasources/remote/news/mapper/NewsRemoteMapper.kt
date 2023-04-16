package com.javimartd.theguardian.v2.data.datasources.remote.news.mapper

import com.javimartd.theguardian.v2.data.datasources.remote.RemoteMapper
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import javax.inject.Inject

class NewsRemoteMapper @Inject constructor(): RemoteMapper<NewsRemoteRaw, List<NewsData>> {

    override fun mapFromRemote(remote: NewsRemoteRaw): List<NewsData> {
        return remote.newsResponse?.results?.map {
            NewsData(
                body = it.fields?.bodyText,
                date = it.webPublicationDate,
                id = it.id,
                sectionId = it.sectionId,
                sectionName = it.sectionName,
                thumbnail = it.fields?.thumbnail,
                title = it.webTitle,
                webUrl = it.webUrl
            )
        }?: emptyList()
    }
}