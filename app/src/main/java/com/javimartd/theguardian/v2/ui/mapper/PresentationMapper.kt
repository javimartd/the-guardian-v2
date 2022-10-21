package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.ui.model.NewsItemUiState

fun NewsEntity.toPresentation() =
    NewsItemUiState(
        date = date,
        body = body?: "",
        title = title,
        thumbnail = thumbnail?: "",
        webUrl = webUrl,
    )