package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.data.datasources.local.news.db.NewsEntity
import com.javimartd.theguardian.v2.utils.randomString

object LocalDataSourceFactory {

    fun getSomeNews(count: Int): List<NewsEntity> {
        val data = mutableListOf<NewsEntity>()
        repeat(count) {
            data.add(makeNews())
        }
        return data
    }

    private fun makeNews(): NewsEntity {
        return NewsEntity(
            newsId = randomString(),
            body = randomString(),
            date = "2021-11-08T16:22:34Z",
            sectionId = randomString(),
            sectionName = randomString(),
            title = randomString(),
            thumbnail = randomString(),
            webUrl = randomString()
        )
    }
}