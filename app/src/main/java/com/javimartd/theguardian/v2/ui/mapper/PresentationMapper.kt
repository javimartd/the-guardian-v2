package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.ui.model.News

fun List<RawNews>?.newsMapToView(): List<News> {
    return this?.map {
        News(
            it.id,
            it.sectionId,
            it.sectionName,
            it.webTitle,
            it.webPublicationDate,
            it.webUrl,
            it.fields?.thumbnail ?: "",
            it.fields?.bodyText ?: ""
        )
    } ?: emptyList()
}

fun List<RawSection>?.sectionsMapToView(): List<String> {
    return this?.flatMap { listOf(it.webTitle) } ?: emptyList()
}