package com.javimartd.theguardian.v2.data.mapper

import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.factory.RemoteDataSourceFactory
import org.junit.Assert
import org.junit.Test

class NewsMapperTest {

    @Test
    fun newsToDomain() {
        val data = RemoteDataSourceFactory.getSomeNews(5)
        val domain = data.map { it.toDomain() }
        assertEqual(data[0], domain[0])
    }

    @Test
    fun sectionsToDomain() {
        val data = RemoteDataSourceFactory.getSomeSections(5)
        val domain = data.map { it.toDomain() }
        assertEqual(data[0], domain[0])
    }

    private fun assertEqual(data: NewsRaw, domain: NewsEntity) {
        Assert.assertEquals(data.fields?.bodyText, domain.body)
        Assert.assertEquals(data.webPublicationDate, domain.date)
        Assert.assertEquals(data.id, domain.id)
        Assert.assertEquals(data.sectionId, domain.sectionId)
        Assert.assertEquals(data.sectionName, domain.sectionName)
        Assert.assertEquals(data.fields?.thumbnail, domain.thumbnail)
        Assert.assertEquals(data.webTitle, domain.title)
        Assert.assertEquals(data.webUrl, domain.webUrl)
    }

    private fun assertEqual(data: SectionRaw, domain: SectionEntity) {
        Assert.assertEquals(data.id, domain.id)
        Assert.assertEquals(data.webTitle, domain.webTitle)
        Assert.assertEquals(data.webUrl, domain.webUrl)
    }
}