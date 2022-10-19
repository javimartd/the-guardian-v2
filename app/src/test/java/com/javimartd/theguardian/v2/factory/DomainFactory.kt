package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity
import com.javimartd.theguardian.v2.utils.randomString

object DomainFactory {

    fun getSomeNews(count: Int): List<NewsEntity> {
        val entities = mutableListOf<NewsEntity>()
        repeat(count) {
            entities.add(makeNews())
        }
        return entities
    }

    fun getSomeSections(count: Int): List<SectionEntity> {
        val entities = mutableListOf<SectionEntity>()
        repeat(count) {
            entities.add(makeSection())
        }
        return entities
    }

    private fun makeNews(): NewsEntity {
        return NewsEntity(
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

    private fun makeSection(): SectionEntity {
        return SectionEntity(
            id = randomString(),
            webTitle = randomString(),
            webUrl = randomString()
        )
    }
}