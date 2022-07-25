package com.javimartd.theguardian.v2.ui.viewmodel

sealed class NewsUiEvent {
    data class SectionSelected(val sectionName: String): NewsUiEvent()
}