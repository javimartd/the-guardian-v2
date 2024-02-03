package com.javimartd.theguardian.v2.features.news

import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState

interface NewsUiContract {
    sealed class State {
        data object Loading: State()
        data class News(val news: List<NewsItemUiState>): State()
        data object NoNews: State()
        data class Error(val errorMessage: Int): State()
    }

    sealed class Intent {
        data class SearchQueryChange(val newQuery: String): Intent()
        data class SearchQueryClick(val sectionId: String = ""): Intent()
        data object OnRefresh: Intent()
    }
}