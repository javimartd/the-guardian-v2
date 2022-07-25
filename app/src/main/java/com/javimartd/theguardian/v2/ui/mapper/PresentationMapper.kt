package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.ui.model.NewsUi

fun List<NewsRaw>?.newsMapToView(): List<NewsUi> {
    return this?.map {
        NewsUi(
            it.webTitle,
            it.webPublicationDate,
            it.webUrl,
            it.fields?.thumbnail ?: "",
            it.fields?.bodyText ?: ""
        )
    } ?: emptyList()
}

fun List<SectionRaw>?.sectionsMapToView(): List<String> {
    return this?.flatMap { listOf(it.webTitle) } ?: emptyList()
}