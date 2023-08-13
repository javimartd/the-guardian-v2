package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.data.datasources.remote.news.model.FieldsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRaw
import com.javimartd.theguardian.v2.utils.randomString

object RemoteDataSourceFactory {

    fun getSomeNews(count: Int): List<NewsRaw> {
        val raw = mutableListOf<NewsRaw>()
        repeat(count) {
            raw.add(makeNews())
        }
        return raw
    }

    private fun makeNews(): NewsRaw {
        return NewsRaw(
            id = randomString(),
            sectionId = randomString(),
            sectionName = randomString(),
            webPublicationDate = "2021-11-08T16:22:34Z",
            webTitle = randomString(),
            webUrl = randomString(),
            fields = FieldsRaw(
                liveBloggingNow = "false",
                thumbnail = randomString(),
                bodyText = randomString()
            )
        )
    }
}