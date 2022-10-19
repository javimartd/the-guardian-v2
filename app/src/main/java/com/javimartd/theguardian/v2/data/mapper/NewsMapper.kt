package com.javimartd.theguardian.v2.data.mapper

import com.javimartd.theguardian.v2.data.datasources.remote.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.SectionRaw
import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.domain.model.SectionEntity

fun NewsRaw.toDomain(): NewsEntity {
    return NewsEntity(
        body = fields?.bodyText,
        date = webPublicationDate,
        id = id,
        sectionId = sectionId,
        sectionName = sectionName,
        thumbnail = fields?.thumbnail,
        title = webTitle,
        webUrl = webUrl
    )
}

fun SectionRaw.toDomain(): SectionEntity {
    return SectionEntity(
        id = id,
        webTitle = webTitle,
        webUrl = webUrl
    )
}