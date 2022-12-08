package com.javimartd.theguardian.v2.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.ui.news.NewsScreen
import com.javimartd.theguardian.v2.ui.settings.SettingsScreen
import org.junit.Rule
import org.junit.Test

class NewsScreenTest {

    // this test rule allows us to set the test content and interact with our app inside the test.
    @get:Rule
    val rule = createComposeRule()

    // to access the activity, use this rule.
    // ComponentActivity is an empty activity that serves as a host for our Compose content.
    // Semantic UI Test: is a test to validate properties, actions, parent and children.
    /*@get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()*/

    @Test
    fun demo() {

        val navHostController = NavHostController()
        // in here we are in a composable context, so we can call any composable function to
        // set up our test scenario.
        rule.setContent { SettingsScreen(navHostController = null) }
        rule.onNodeWithText("The guardian")

        // our test rule contains different finder methods to search for elements in our screen.
        // In compose, elements on the screen are represented by nodes
    }
}