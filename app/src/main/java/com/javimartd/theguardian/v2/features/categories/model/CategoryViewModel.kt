package com.javimartd.theguardian.v2.features.categories.model

import androidx.lifecycle.SavedStateHandle
import com.javimartd.theguardian.v2.features.categories.CategoryUiContract
import com.javimartd.theguardian.v2.features.categories.navigation.CategoryNavigator
import com.javimartd.theguardian.v2.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<
    CategoryUiContract.CategoryUiState,
    CategoryUiContract.CategoryEvents,
    CategoryUiContract.CategoryEffects
>() {

    //private val arg = savedStateHandle.get<String>(GameNavigator.FIRST_ARG).orEmpty()
    private val arg = savedStateHandle.get<CategoryUiState>(CategoryNavigator.FIRST_ARG)

    override fun initState(): CategoryUiContract.CategoryUiState {
        return CategoryUiContract.CategoryUiState(isLoading = true)
    }

    override fun handleEvents(events: CategoryUiContract.CategoryEvents) {
        when (events) {
            CategoryUiContract.CategoryEvents.CustomEvents -> {
                // ...
            }
        }
    }

}