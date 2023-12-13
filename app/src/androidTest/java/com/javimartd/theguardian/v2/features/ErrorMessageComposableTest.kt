package com.javimartd.theguardian.v2.features

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.features.news.view.ErrorMessage
import com.javimartd.theguardian.v2.features.news.view.NoNewsMessage
import com.javimartd.theguardian.v2.ui.theme.TheGuardianTheme
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.ui.components.Tags
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackBarHost
import org.junit.Rule
import org.junit.Test
import androidx.compose.runtime.remember as remember1

class ErrorMessageComposableTest {

    @get:Rule
    val composableTestRule = createComposeRule()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun display_error_message() {

        val snackBarHostState = SnackbarHostState()
        composableTestRule.setContent {
            Scaffold(
                snackbarHost = { TheGuardianSnackBarHost(hostState = snackBarHostState) }
            ) {
                ErrorMessage(
                    uiState = NewsUiContract.NewsUiState(
                        isRefreshing = false,
                        sectionSelected = "",
                        sections = emptyList(),
                        news = emptyList(),
                        errorMessage = R.string.generic_error_message
                    ),
                    snackBarHostState = snackBarHostState
                )
            }
        }

        composableTestRule
            .onNodeWithTag(Tags.TAG_SNACK_BAR)
            .assertIsDisplayed()
    }
}