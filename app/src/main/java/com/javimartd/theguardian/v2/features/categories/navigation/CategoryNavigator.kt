package com.javimartd.theguardian.v2.features.categories.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.features.categories.model.categoryUiState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class CategoryNavigator(navController: NavHostController) {

    val actionNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val actionNavigateForwardTo: (String) -> Unit = { destination ->
        navController.navigate(route = destination)
    }

    val actionNavigateToSecondView: (Int) -> Unit = { int ->
        val path = SECOND.replace("{$FIRST_ARG}", "$int")
        //val path = "$ROOT/category_second_view/${int}"
        navController.navigate(path)
    }

    val actionNavigateToThirdView: (String, String) -> Unit = { firstArg, secondArg ->
        val path = THIRD
            .replace("{$FIRST_ARG}", firstArg)
            .replace("{$SECOND_ARG}", secondArg)
        navController.navigate(path)
    }

    val actionNavigateToFourthView: () -> Unit = {
        val path = FOURTH.replace(
            "{$FIRST_ARG}",
            Uri.encode(Json.encodeToJsonElement(categoryUiState).toString())
        )
        navController.navigate(path)
    }

    companion object {

        // arguments
        const val FIRST_ARG = "first_arg"
        const val SECOND_ARG = "second_arg"

        const val ROOT = "category_graph"
        const val START = "$ROOT/first_category_view"
        const val SECOND = "$ROOT/second_category_view/{$FIRST_ARG}"
        const val THIRD = "$ROOT/third_category_view/{$FIRST_ARG}/{$SECOND_ARG}"
        const val FOURTH = "$ROOT/fourth_category_view/{$FIRST_ARG}"
    }
}