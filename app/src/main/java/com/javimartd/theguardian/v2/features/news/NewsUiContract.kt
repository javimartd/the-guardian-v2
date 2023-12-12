package com.javimartd.theguardian.v2.features.news

import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState

interface NewsUiContract {

    data class NewsUiState(
        val isRefreshing: Boolean = true,
        val sectionSelected: String = "Choose a section of interest",
        val sections: List<String> = emptyList(),
        val news: List<NewsItemUiState> = emptyList(),
        val errorMessage: Int? = null
    )

    sealed class NewsUiEvent {
        data class GetNews(val sectionName: String = ""): NewsUiEvent()
    }
}