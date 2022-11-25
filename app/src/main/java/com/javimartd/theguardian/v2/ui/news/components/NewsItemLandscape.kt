package com.javimartd.theguardian.v2.ui.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.ui.news.model.NewsItemUiState

@Composable
fun NewsItemLandscape(uiState: NewsItemUiState) {
    NewsItem {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_margin))) {
            Row {
                Thumbnail(
                    modifier = Modifier.weight(0.5f),
                    thumbnail = uiState.thumbnail
                )
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
                ) {
                    Title(title = uiState.title)
                    Section(
                        name = uiState.sectionName,
                        date = uiState.date
                    )
                }
            }
            Body(body = uiState.body)
            ReadMore(webUrl = uiState.webUrl)
        }
    }
}