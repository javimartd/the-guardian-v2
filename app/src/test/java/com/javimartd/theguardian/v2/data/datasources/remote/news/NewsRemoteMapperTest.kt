package com.javimartd.theguardian.v2.data.datasources.remote.news

import com.javimartd.theguardian.v2.data.datasources.remote.RemoteMapper
import com.javimartd.theguardian.v2.data.datasources.remote.news.mapper.NewsRemoteMapper
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRemoteRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsResultsRaw
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.factory.RemoteDataSourceFactory
import org.junit.Assert
import org.junit.Test

internal class NewsLocalMapperTest {

    private val sut: RemoteMapper<NewsRemoteRaw, List<NewsData>> = NewsRemoteMapper()

    @Test
    fun mapFromLocal() {
        val remoteNewsRawList = RemoteDataSourceFactory.getSomeNews(1)
        val remote = NewsRemoteRaw(NewsResultsRaw("ok", remoteNewsRawList))

        val raw = remoteNewsRawList[0]
        val repository = sut.mapFromRemote(remote)[0]

        Assert.assertEquals(raw.fields?.bodyText, repository.body)
        Assert.assertEquals(raw.webPublicationDate, repository.date)
        Assert.assertEquals(raw.id, repository.id)
        Assert.assertEquals(raw.sectionId, repository.sectionId)
        Assert.assertEquals(raw.sectionName, repository.sectionName)
        Assert.assertEquals(raw.fields?.thumbnail, repository.thumbnail)
        Assert.assertEquals(raw.webTitle, repository.title)
        Assert.assertEquals(raw.webUrl, repository.webUrl)
    }
}