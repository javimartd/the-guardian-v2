package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.utils.randomString

object DomainFactory {

    fun getSomeNews(count: Int): List<News> {
        val entities = mutableListOf<News>()
        repeat(count) {
            entities.add(makeNews())
        }
        return entities
    }

    fun getSomeSections(count: Int): List<Section> {
        val entities = mutableListOf<Section>()
        repeat(count) {
            entities.add(makeSection())
        }
        return entities
    }

    private fun makeNews(): News {
        return News(
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

    private fun makeSection(): Section {
        return Section(
            id = randomString(),
            webTitle = randomString(),
            webUrl = randomString()
        )
    }
}