package com.javimartd.theguardian.v2.features.news.model

data class NewsUiState(
    val isRefreshing: Boolean = false,
    val sectionSelected: String = "Choose a section of interest",
    val sections: List<String> = emptyList(),
    val news: List<NewsItemUiState> = emptyList(),
    val errorMessage: Int? = null
)
