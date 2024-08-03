package com.javimartd.theguardian.v2.features.culture.navigation

import androidx.navigation.NavHostController

class CultureNavigator(navController: NavHostController) {

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val actionNavigateForwardTo: (String) -> Unit = { destination ->
        navController.navigate(destination)
    }

    val actionNavigateBackTo: (String) -> Unit = { destination ->
        navController.popBackStack(
            route = destination,
            inclusive = false // Whether the given destination should also be popped
        )
    }
    
    val actionPopUpTo: () -> Unit = {
        navController.navigate(route = FOURTH) {
            popUpTo(FIRST) { inclusive = true }
        }
    }

    val actionNavigateToItself: () -> Unit = {
        navController.navigate(THIRD) {
            launchSingleTop = true // avoiding multiple copies on the top of the back stack
        }
    }

    companion object {
        const val ROOT = "culture_graph"
        const val FIRST = "culture_first_view"
        const val SECOND = "culture_second_view"
        const val THIRD = "culture_third_view"
        const val FOURTH = "culture_fourth_view"
    }
}

