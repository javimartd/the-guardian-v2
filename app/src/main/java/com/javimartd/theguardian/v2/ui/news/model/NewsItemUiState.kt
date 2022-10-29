package com.javimartd.theguardian.v2.ui.news.model

data class NewsItemUiState(
    val body: String,
    val date: String,
    val id: String,
    val sectionName: String,
    val thumbnail: String,
    val title: String,
    val webUrl: String
)