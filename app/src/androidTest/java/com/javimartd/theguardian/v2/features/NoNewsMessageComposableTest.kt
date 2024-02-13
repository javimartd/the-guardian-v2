package com.javimartd.theguardian.v2.features

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.components.Tags
import com.javimartd.theguardian.v2.features.news.view.NoNewsContent
import com.javimartd.theguardian.v2.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class NoNewsMessageComposableTest {
    // it creates an activity that serves as a host activity for out composable
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun display_no_news_message() {
        rule.activity.setContent {
            NoNewsContent()
        }
        rule
            .onNodeWithTag(Tags.TAG_NEWS_SCREEN_NO_NEWS_MESSAGE)
            .assertIsDisplayed()
            .assertTextEquals(rule.activity.getString(R.string.news_screen_no_news_message))
    }
}