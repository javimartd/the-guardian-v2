package com.javimartd.theguardian.v2.features.categories.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CategoryUiState(
    val id: String,
    val name: String,
    val url: String
): Parcelable

val categoryUiState = CategoryUiState(
    id = "id",
    name = "Monopoly",
    url = "https://www.theguardian.com/international"
)
