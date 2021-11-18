package com.javimartd.theguardian.v2.ui.state

import com.javimartd.theguardian.v2.ui.model.News

sealed class NewsViewState {
    object Loading: NewsViewState()
    data class ShowNews(val news: List<News>): NewsViewState()
    data class ShowNewsAndSections(
        val news: List<News>,
        val sections: List<String>
    ): NewsViewState()
    object ShowNetworkError: NewsViewState()
    object ShowGenericError: NewsViewState()
}
