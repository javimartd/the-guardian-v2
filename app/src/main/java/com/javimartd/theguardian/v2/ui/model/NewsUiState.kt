package com.javimartd.theguardian.v2.ui.model

sealed class NewsUiState {
    object Loading: NewsUiState()
    data class ShowNews(val news: List<NewsUi>): NewsUiState()
    data class ShowSections(val sections: List<String>): NewsUiState()
    data class ShowError(val message: Int): NewsUiState()
}

data class NewsUi(
    val title: String,
    val date: String,
    val webUrl: String,
    val thumbnail: String,
    val description: String
)
