package com.javimartd.theguardian.v2.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.model.NewsUiState
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import com.javimartd.theguardian.v2.ui.components.TAG_NO_NEWS_MESSAGE
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@HiltAndroidTest
class NewsScreenTest {

    private val ctx = InstrumentationRegistry.getInstrumentation().context

    /*@get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()*/

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    //@Mock lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        //MockitoAnnotations.openMocks(this)
    }

    @Test
    fun display_no_news_message() {
        val viewModel = mockk<NewsViewModel>(relaxed = true)
        every { viewModel.uiState } returns NewsUiState(
            isRefreshing = false,
            sectionSelected = "",
            sections = emptyList(),
            news = emptyList(),
            errorMessage = null
        )

       /*Mockito
            .`when`(viewModel.uiState)
            .thenReturn(
                NewsUiState(
                    isRefreshing = false,
                    sectionSelected = "",
                    sections = emptyList(),
                    news = emptyList(),
                    errorMessage = null
                )
            )*/

        composeTestRule
            .onNodeWithTag(TAG_NO_NEWS_MESSAGE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TAG_NO_NEWS_MESSAGE)
            .assertTextEquals(ctx.getString(R.string.news_screen_no_news_message))
    }

    /*@Test
    fun displayNewsContent() {
        val news = PresentationFactory.getSomeNews(3)
        val viewModel = mockk<NewsViewModel>(relaxed = true)
        every { viewModel.uiState } returns NewsUiState(
            isRefreshing = false,
            sectionSelected = "Sport",
            sections = listOf("Sport", "Politics", "Business"),
            news = news,
            errorMessage = null
        )

        composeTestRule.setContent {
            NewsScreen(
                navController = mockk(relaxed = true),
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Sport").assertIsDisplayed()
        composeTestRule.onNodeWithText("Article 1").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("sections dropdown icon content description").performClick()
        composeTestRule.onNodeWithText("Politics").performClick()
        composeTestRule.onNodeWithText("Article 2").assertIsDisplayed()
    }*/

    /*@Test
    fun displayErrorMessage() {

        val viewModel = mockk<NewsViewModel>(relaxed = true)
        every { viewModel.uiState } returns NewsUiState(
            errorMessage = stringResource(id = R.string.network_error_message)
        )

        composeTestRule.setContent {
            NewsScreen(
                navController = mockk(relaxed = true),
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText(R.string.network_error).assertIsDisplayed()
    }*/
}