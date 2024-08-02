package com.javimartd.theguardian.v2.features.categories

import com.javimartd.navwizard.common.BaseContract

interface CategoryUiContract: BaseContract {

    data class CategoryUiState(
        override var isLoading: Boolean
    ): BaseContract.UiState

    sealed interface CategoryEvents: BaseContract.Events {
        data object CustomEvents: CategoryEvents
    }

    sealed interface CategoryEffects: BaseContract.Effects {
        data object CustomEffects: CategoryEffects
    }
}