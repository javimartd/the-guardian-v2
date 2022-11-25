package com.javimartd.theguardian.v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.javimartd.theguardian.v2.ui.SettingsScreen
import com.javimartd.theguardian.v2.ui.news.NewsScreen
import com.javimartd.theguardian.v2.ui.news.NewsViewModel

@Composable
fun TheGuardianNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TheGuardianDestinations.NEWS_ROUTE,
    ) {
        composable(TheGuardianDestinations.NEWS_ROUTE) {
            val viewModel = hiltViewModel<NewsViewModel>()
            NewsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(TheGuardianDestinations.SETTINGS_ROUTE) {
            SettingsScreen(navController)
        }
    }
}