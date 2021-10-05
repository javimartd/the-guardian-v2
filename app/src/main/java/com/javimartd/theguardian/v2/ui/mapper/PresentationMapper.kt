package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.data.datasources.model.RawNews
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.model.Section

fun List<RawNews>?.toNewsView(): List<News> {
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

fun List<RawSection>?.toSectionsView(): List<Section> {
    return this?.map {
        Section(
            it.id,
            it.webTitle,
            it.webUrl
        )
    } ?: emptyList()
}