package com.javimartd.theguardian.v2.features.settings.navigation

import androidx.navigation.NavHostController

class SettingsNavigator(navHostController: NavHostController) {

    val actionNavigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    companion object {
        const val ROOT = "settings_graph"
        const val START = "settings_view"
    }
}