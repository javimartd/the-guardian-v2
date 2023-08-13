package com.javimartd.theguardian.v2.factory

import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import com.javimartd.theguardian.v2.randomString

object PresentationFactory {

    fun getSomeNews(count: Int): List<NewsItemUiState> {
        val uiState = mutableListOf<NewsItemUiState>()
        repeat(count) {
            uiState.add(makeNews())
        }
        return uiState
    }

    private fun makeNews(): NewsItemUiState {
        return NewsItemUiState(
            body = randomString(),
            date = "2022-10-11T15:49:25Z",
            id = randomString(),
            sectionName = randomString(),
            thumbnail = randomString(),
            title = randomString(),
            webUrl = randomString(),
        )
    }
}