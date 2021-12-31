package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection

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

    private fun makeNews(): RawNews {
        return RawNews(
            id = "1",
            sectionId = "sectionId",
            sectionName = "sectionName",
            webPublicationDate = "2021-11-08T16:22:34Z",
            webTitle = "webTitle",
            webUrl = "webUrl",
            fields = null
        )
    }

    private fun makeSection(): RawSection {
        return RawSection(
            id = "id",
            webTitle = "webTitle",
            webUrl = "webUrl"
        )
    }
}