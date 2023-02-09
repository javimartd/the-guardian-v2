package com.javimartd.theguardian.v2.features.news

interface NewsUiContract {

    sealed class NewsUiEvent {
        data class GetNews(val sectionName: String = ""): NewsUiEvent()
        data class ReadNews(val url: String = ""): NewsUiEvent()
        object NavigateToSettings: NewsUiEvent()
    }

    sealed class NewsUiEffect {
        object NavigateToSettings: NewsUiEffect()
    }
}