package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.model.RawFields
import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import java.util.*

object DataFactory {

    fun makeNews(count: Int): List<RawNews> {
        val data = mutableListOf<RawNews>()
        repeat(count) {
            data.add(makeNews())
        }
        return data
    }

    fun makeSections(count: Int): List<RawSection> {
        val data = mutableListOf<RawSection>()
        repeat(count) {
            data.add(makeSection())
        }
        return data
    }

    private fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun makeNews(): RawNews {
        return RawNews(
            id = randomString(),
            sectionId = randomString(),
            sectionName = randomString(),
            webPublicationDate = "2021-11-08T16:22:34Z",
            webTitle = randomString(),
            webUrl = randomString(),
            fields = RawFields(
                liveBloggingNow = "false",
                thumbnail = randomString(),
                bodyText = randomString()
            )
        )
    }

    private fun makeSection(): RawSection {
        return RawSection(
            id = randomString(),
            webTitle = randomString(),
            webUrl = randomString()
        )
    }
}