package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.utils.randomString

object RepositoryFactory {

    fun getSomeNews(count: Int): List<NewsData> {
        val data = mutableListOf<NewsData>()
        repeat(count) {
            data.add(makeNews())
        }
        return data
    }

    fun getSomeSections(count: Int): List<SectionData> {
        val data = mutableListOf<SectionData>()
        repeat(count) {
            data.add(makeSection())
        }
        return data
    }

    private fun makeNews(): NewsData {
        return NewsData(
            body = randomString(),
            date = "2022-10-11T15:49:25Z",
            id = randomString(),
            sectionId = randomString(),
            sectionName = randomString(),
            thumbnail = randomString(),
            title = randomString(),
            webUrl = randomString(),
        )
    }

    private fun makeSection(): SectionData {
        return SectionData(
            id = randomString(),
            webTitle = randomString(),
            webUrl = randomString()
        )
    }
}