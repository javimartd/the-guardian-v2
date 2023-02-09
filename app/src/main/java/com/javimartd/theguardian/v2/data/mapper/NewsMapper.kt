package com.javimartd.theguardian.v2.data.mapper

import com.javimartd.theguardian.v2.data.datasources.local.model.NewsLocalData
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.NewsRaw
import com.javimartd.theguardian.v2.data.datasources.remote.news.model.SectionRaw
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

fun NewsEntity.toData(): NewsLocalData {
    return NewsLocalData(
        body = body?: "",
        date = date,
        newsId = id,
        sectionId = sectionId,
        sectionName = sectionName,
        title = title,
        thumbnail = thumbnail?: "",
        webUrl = webUrl
    )
}

fun NewsLocalData.toDomain(): NewsEntity {
    return NewsEntity(
        body = body,
        date = date,
        id = newsId,
        sectionId = sectionId,
        sectionName = sectionName,
        thumbnail = thumbnail,
        title = title,
        webUrl = webUrl
    )
}