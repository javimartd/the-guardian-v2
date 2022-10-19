package com.javimartd.theguardian.v2.ui.mapper

import com.javimartd.theguardian.v2.domain.model.NewsEntity
import com.javimartd.theguardian.v2.ui.model.NewsUi

fun NewsEntity.toPresentation() =
    NewsUi(
        date = date,
        body = body?: "",
        title = title,
        thumbnail = thumbnail?: "",
        webUrl = webUrl,
    )