package com.javimartd.theguardian.v2.features.news.mapper

import com.javimartd.theguardian.v2.domain.news.model.News
import com.javimartd.theguardian.v2.features.extensions.DATE_TIME_API_FORMAT
import com.javimartd.theguardian.v2.features.extensions.toDate
import com.javimartd.theguardian.v2.features.extensions.toLong
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState

fun News.toPresentation(): NewsItemUiState {
    return NewsItemUiState(
        body = body?: "",
        date = date.toLong(DATE_TIME_API_FORMAT).toDate(),
        id = id,
        sectionName = sectionName,
        thumbnail = thumbnail?: "",
        title = title,
        webUrl = webUrl
    )
}