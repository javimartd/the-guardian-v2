package com.javimartd.theguardian.v2.data.repository.news.mapper

import com.javimartd.theguardian.v2.data.datasources.local.news.db.NewsEntity
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.domain.news.model.Section

fun NewsData.toDomain(): News {
    return News(
        body = body,
        date = date,
        id = id,
        sectionId = sectionId,
        sectionName = sectionName,
        thumbnail = thumbnail,
        title = title,
        webUrl = webUrl
    )
}

fun SectionData.toDomain(): Section {
    return Section(
        id = id,
        webTitle = webTitle,
        webUrl = webUrl
    )
}

fun News.toData(): NewsEntity {
    return NewsEntity(
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

fun NewsEntity.toDomain(): News {
    return News(
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