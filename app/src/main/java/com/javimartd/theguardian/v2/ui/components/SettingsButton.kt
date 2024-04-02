package com.javimartd.theguardian.v2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.components.Tags

@Composable
fun SettingsButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.testTag(Tags.TAG_NEWS_SCREEN_SETTINGS),
        onClick = onClick
    ) {
        Icon(
            contentDescription = stringResource(id = R.string.news_screen_settings_icon_content_description),
            imageVector = Icons.Default.Settings,
            tint = Color.Black
        )
    }
}