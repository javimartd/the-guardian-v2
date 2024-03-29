package com.javimartd.theguardian.v2.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.javimartd.theguardian.v2.features.news.components.Tags
import com.javimartd.theguardian.v2.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class NewsScreenTest {

    private val ctx = InstrumentationRegistry.getInstrumentation().context

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun toolbar_should_be_displayed() {
        composeTestRule
            .onNodeWithTag(Tags.TAG_NEWS_SCREEN_TOOLBAR)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(Tags.TAG_NEWS_SCREEN_SETTINGS)
            .assertIsDisplayed()
    }
}