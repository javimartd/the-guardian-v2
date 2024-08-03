package com.javimartd.theguardian.v2.features.culture.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.javimartd.theguardian.v2.features.culture.views.CultureFirstView
import com.javimartd.theguardian.v2.features.culture.views.CultureFourthView
import com.javimartd.theguardian.v2.features.culture.views.CultureSecondView
import com.javimartd.theguardian.v2.features.culture.views.CultureThirdView

fun NavGraphBuilder.cultureNavigationGraph(navigator: CultureNavigator) {
    navigation(
        route = CultureNavigator.ROOT,
        startDestination = CultureNavigator.FIRST
    ) {
        composable(route = CultureNavigator.FIRST) {
            CultureFirstView(navigator)
        }
        composable(route = CultureNavigator.SECOND) {
            CultureSecondView(navigator)
        }
        composable(route = CultureNavigator.THIRD) {
            CultureThirdView(navigator)
        }
        composable(route = CultureNavigator.FOURTH) {
            CultureFourthView(navigator)
        }
    }
}