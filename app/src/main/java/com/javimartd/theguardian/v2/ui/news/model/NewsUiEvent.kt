package com.javimartd.theguardian.v2.ui.news.model

sealed class NewsUiEvent {
    data class OnGetNews(val sectionName: String = ""): NewsUiEvent()
}
