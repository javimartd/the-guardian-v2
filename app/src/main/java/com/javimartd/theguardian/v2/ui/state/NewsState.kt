package com.javimartd.theguardian.v2.ui.state

import com.javimartd.theguardian.v2.ui.model.News
import com.javimartd.theguardian.v2.ui.model.Section

sealed class NewsState {
    object Loading: NewsState()
    data class Success(
        val news: List<News>,
    ): NewsState()
}
