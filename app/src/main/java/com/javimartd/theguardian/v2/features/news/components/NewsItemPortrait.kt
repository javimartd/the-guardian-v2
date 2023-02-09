package com.javimartd.theguardian.v2.features.news.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import com.javimartd.theguardian.v2.ui.theme.TheGuardianTheme

@Composable
fun NewsItemPortrait(uiState: NewsItemUiState) {
   NewsItem {
       Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))) {
           Thumbnail(thumbnail = uiState.thumbnail)
           Title(title = uiState.title)
           Section(
               date = uiState.date,
               name = uiState.sectionName
           )
           Body(body = uiState.body)
           ReadMore(webUrl = uiState.webUrl)
       }
   }
}

@Preview(
    name = "News Item Portrait",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun NewsItemPortraitPreview() {
    TheGuardianTheme() {
        Surface {
            NewsItemPortrait(
                NewsItemUiState(
                    body = "This is the body",
                    date = "2 Aug, 2022",
                    id = "id",
                    sectionName = "World news",
                    thumbnail = "",
                    title = "This is the title",
                    webUrl = "https://www.theguardian.com/international"
                )
            )
        }
    }
}
