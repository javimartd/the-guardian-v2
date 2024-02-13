package com.javimartd.theguardian.v2.features

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.view.ErrorMessageContent
import com.javimartd.theguardian.v2.ui.components.Tags
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackBarHost
import org.junit.Rule
import org.junit.Test

class ErrorMessageComposableTest {

    @get:Rule
    val composableTestRule = createComposeRule()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Test
    fun display_error_message() {
        val snackBarHostState = SnackbarHostState()
        composableTestRule.setContent {
            Scaffold(
                snackbarHost = { TheGuardianSnackBarHost(hostState = snackBarHostState) }
            ) {
                ErrorMessageContent(
                    message = R.string.generic_error_message,
                    snackBarHostState = snackBarHostState
                )
            }
        }
        composableTestRule
            .onNodeWithTag(Tags.TAG_SNACK_BAR)
            .assertIsDisplayed()
    }
}