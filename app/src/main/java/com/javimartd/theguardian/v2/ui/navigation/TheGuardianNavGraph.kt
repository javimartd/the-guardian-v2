package com.javimartd.theguardian.v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.javimartd.theguardian.v2.features.news.navigation.NewsNavigator
import com.javimartd.theguardian.v2.features.news.navigation.newsNavigationGraph
import com.javimartd.theguardian.v2.features.settings.navigation.SettingsNavigator
import com.javimartd.theguardian.v2.features.settings.navigation.settingsNavigationGraph

@Composable
fun TheGuardianNavGraph() {

    // NavController manages app navigation within a NavHost
    val navController = rememberNavController()

    val startDestination: String = NewsNavigator.ROOT

    val newsNavigator = NewsNavigator(navController)
    val settingsNavigator = SettingsNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination // app start destination
    ) {
        newsNavigationGraph(newsNavigator)
        settingsNavigationGraph(settingsNavigator)
    }
}