package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.data.datasources.remote.FieldsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import java.util.*

object DataFactory {

    fun getSomeNews(count: Int): List<NewsRaw> {
        val data = mutableListOf<NewsRaw>()
        repeat(count) {
            data.add(makeNews())
        }
        return data
    }

    fun getSomeSections(count: Int): List<SectionRaw> {
        val data = mutableListOf<SectionRaw>()
        repeat(count) {
            data.add(makeSection())
        }
        return data
    }

    private fun randomString(): String {
        return UUID.randomUUID().toString()
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

    private fun makeSection(): SectionRaw {
        return SectionRaw(
            id = randomString(),
            webTitle = randomString(),
            webUrl = randomString()
        )
    }
}