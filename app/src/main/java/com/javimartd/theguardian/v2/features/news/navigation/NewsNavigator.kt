package com.javimartd.theguardian.v2.features.news.navigation

import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.features.categories.navigation.CategoryNavigator
import com.javimartd.theguardian.v2.features.settings.navigation.SettingsNavigator

class NewsNavigator(navHostController: NavHostController) {

    companion object {
        const val ROOT = "news_graph"
        const val START = "news_view"
    }

    val actionNavigateToSettings: () -> Unit = {
        navHostController.navigate(route = SettingsNavigator.START)
    }

    val actionNavigateToCategories: () -> Unit = {
        navHostController.navigate(route = CategoryNavigator.START)
    }
}