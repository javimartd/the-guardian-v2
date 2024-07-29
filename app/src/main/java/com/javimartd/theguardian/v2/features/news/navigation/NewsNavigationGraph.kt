package com.javimartd.theguardian.v2.features.news.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import com.javimartd.theguardian.v2.features.news.view.NewsScreen

fun NavGraphBuilder.newsNavigationGraph(navigator: NewsNavigator) {

    // construct a nested NavGraph
    navigation(
        route = NewsNavigator.ROOT,
        startDestination = NewsNavigator.START
    ) {

        composable(route = NewsNavigator.START) {
            val viewModel = hiltViewModel<NewsViewModel>()
            NewsScreen(
                navigator = navigator,
                viewModel = viewModel
            )
        }
    }
}