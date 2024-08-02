package com.javimartd.theguardian.v2.features.categories.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.javimartd.theguardian.v2.features.categories.model.CategoryUiState
import com.javimartd.theguardian.v2.features.categories.model.CategoryViewModel
import com.javimartd.theguardian.v2.features.categories.views.CategoryFirstView
import com.javimartd.theguardian.v2.features.categories.views.CategoryFourthView
import com.javimartd.theguardian.v2.features.categories.views.CategorySecondView
import com.javimartd.theguardian.v2.features.categories.views.CategoryThirdView
import com.javimartd.theguardian.v2.ui.navigation.getCustomParcelable
import com.javimartd.theguardian.v2.ui.navigation.parcelableTypeOf

fun NavGraphBuilder.categoryNavigationGraph(navigator: CategoryNavigator) {

    // construct a nested NavGraph
    navigation(
        route = CategoryNavigator.ROOT,
        startDestination = CategoryNavigator.START
    ) {

        // first composable
        composable(route = CategoryNavigator.START) {
            CategoryFirstView(navigator)
        }

        val secondViewArguments = listOf(
            navArgument(CategoryNavigator.FIRST_ARG) {
                nullable = false
                defaultValue = 0
                type = NavType.IntType
            }
        )

        // second composable
        composable(
            arguments = secondViewArguments,
            route = CategoryNavigator.SECOND
        ) { navBackStackEntry ->
            CategorySecondView(
                navigator = navigator,
                firstArg = navBackStackEntry.arguments!!.getInt(CategoryNavigator.FIRST_ARG)
            )
        }

        // third composable
        val thirdViewArguments = listOf(
            navArgument(CategoryNavigator.FIRST_ARG) { type = NavType.StringType },
            navArgument(CategoryNavigator.SECOND_ARG) { type = NavType.StringType }
        )
        composable(
            arguments = thirdViewArguments,
            route = CategoryNavigator.THIRD
        ) { navBackStackEntry ->
            CategoryThirdView(
                navigator = navigator,
                firstArg = navBackStackEntry.arguments?.getString(CategoryNavigator.FIRST_ARG),
                secondArg = navBackStackEntry.arguments?.getString(CategoryNavigator.SECOND_ARG)
            )
        }

        // four composable
        val fourthViewArguments = listOf(
            navArgument(CategoryNavigator.FIRST_ARG) {
                type = NavType.parcelableTypeOf<CategoryUiState>()
            }
        )
        composable(
            arguments = fourthViewArguments,
            route = CategoryNavigator.FOURTH
        ) {navBackStackEntry ->
            val viewModel = hiltViewModel<CategoryViewModel>()
            CategoryFourthView(
                navigator = navigator,
                viewModel =  viewModel,
                firstArg = navBackStackEntry.arguments?.getCustomParcelable<CategoryUiState>(
                    CategoryNavigator.FIRST_ARG
                )
            )
        }
    }
}