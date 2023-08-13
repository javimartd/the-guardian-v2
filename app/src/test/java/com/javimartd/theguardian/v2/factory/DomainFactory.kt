package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section
import com.javimartd.theguardian.v2.utils.randomString

object DomainFactory {

    fun getSomeNews(count: Int): List<News> {
        val domain = mutableListOf<News>()
        repeat(count) {
            domain.add(makeNews())
        }
        return domain
    }

    fun getSomeSections(count: Int): List<Section> {
        val domain = mutableListOf<Section>()
        repeat(count) {
            domain.add(makeSection())
        }
        return domain
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