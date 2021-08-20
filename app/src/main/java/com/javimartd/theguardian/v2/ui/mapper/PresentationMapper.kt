package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.data.remote.model.NewsResults
import com.javimartd.theguardian.v2.data.remote.model.SectionsResults
import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.model.Section

fun NewsResults.mapToPresentation(): List<News> {
    return results?.map {
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

fun SectionsResults.mapToPresentation(): List<Section> {
    return results?.map {
        Section(
            it.id,
            it.webTitle,
            it.webUrl
        )
    } ?: emptyList()
}