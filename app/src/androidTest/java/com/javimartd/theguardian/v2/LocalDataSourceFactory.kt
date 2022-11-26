package com.javimartd.theguardian.v2

import com.javimartd.theguardian.v2.data.datasources.local.model.NewsLocalData

object LocalDataSourceFactory {

    fun getSomeNews(count: Int): List<NewsLocalData> {
        val data = mutableListOf<NewsLocalData>()
        repeat(count) {
            data.add(makeNews())
        }
        return data
    }

    private fun makeNews(): NewsLocalData {
        return NewsLocalData(
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