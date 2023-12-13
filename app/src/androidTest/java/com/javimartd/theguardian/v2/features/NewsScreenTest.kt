package com.javimartd.theguardian.v2.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.javimartd.theguardian.v2.features.news.components.Tags
import com.javimartd.theguardian.v2.ui.MainActivity
import org.junit.Rule
import org.junit.Test

//@HiltAndroidTest
class NewsScreenTest {

    private val ctx = InstrumentationRegistry.getInstrumentation().context

    /*@get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)*/

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    // val composeTestRule = createComposeRule()

    /*@Before
    fun setUp() {
        hiltRule.inject()
    }*/

    @Test
    fun toolbar_should_be_displayed() {
        composeTestRule
            .onNodeWithTag(Tags.TAG_NEWS_SCREEN_TOOLBAR)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(Tags.TAG_NEWS_SCREEN_SETTINGS)
            .assertIsDisplayed()
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