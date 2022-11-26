package com.javimartd.theguardian.v2.ui.news.mapper

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.ui.extensions.DATE_TIME_API_FORMAT
import com.javimartd.theguardian.v2.ui.extensions.toDate
import com.javimartd.theguardian.v2.ui.extensions.toLong
import com.javimartd.theguardian.v2.ui.news.model.NewsItemUiState

fun NewsEntity.toPresentation(): NewsItemUiState {
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