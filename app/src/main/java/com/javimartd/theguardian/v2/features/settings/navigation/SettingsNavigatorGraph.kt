package com.javimartd.theguardian.v2.features.settings.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.javimartd.theguardian.v2.features.settings.view.SettingsScreen
import com.javimartd.theguardian.v2.features.settings.view.SettingsViewModel

fun NavGraphBuilder.settingsNavigationGraph(navigator: SettingsNavigator) {

    // construct a nested NavGraph
    navigation(
        route = SettingsNavigator.ROOT,
        startDestination = SettingsNavigator.START
    ) {

        composable(route = SettingsNavigator.START) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                navigator = navigator,
                viewModel = viewModel
            )
        }
    }
}