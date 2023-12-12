package com.javimartd.theguardian.v2.features.news.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.domain.news.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.news.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.features.news.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSectionsUseCase: GetSectionsUseCase
): ViewModel() {

    var uiState by mutableStateOf(NewsUiContract.NewsUiState())
        private set

    init {
        getAll()
    }

    fun onEvent(event: NewsUiContract.NewsUiEvent) {
        when (event) {
            is NewsUiContract.NewsUiEvent.GetNews -> {
                getNews(event.sectionName)
            }
        }
    }

    fun getAll() {
        uiState = uiState.copy(isRefreshing = true)
        viewModelScope.launch {
            coroutineScope {
                val deferredNews = async { getNewsUseCase.invoke("") }
                val deferredSections = async { getSectionsUseCase.invoke() }
                val newsResponse = deferredNews.await()
                val sectionsResponse = deferredSections.await()
                newsResponse
                    .onSuccess { data ->
                        uiState = uiState.copy(
                            isRefreshing = false,
                            news = data.map { it.toPresentation() }
                        )
                    }
                    .onFailure { handleError(it) }
                sectionsResponse
                    .onSuccess { data ->
                        uiState = uiState.copy(
                            isRefreshing = false,
                            sections = data.map { it.webTitle }
                        )
                    }
                    .onFailure { handleError(it) }
            }
        }
    }

    fun getNews(sectionName: String) {
        uiState = uiState.copy(isRefreshing = true)
        viewModelScope.launch {
            val response = getNewsUseCase(sectionName)
            response.fold(
                onSuccess = { data ->
                    uiState = uiState.copy(
                        isRefreshing = false,
                        sectionSelected = sectionName,
                        news = data.map { it.toPresentation() }
                    )
                },
                onFailure = {
                    handleError(it)
                }
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        val errorMessage = when (throwable as ErrorTypes) {
            is ErrorTypes.RemoteErrors.Network -> R.string.network_error_message
            is ErrorTypes.RemoteErrors.Server -> R.string.server_error_message
            else -> R.string.generic_error_message
        }
        uiState = uiState.copy(
            isRefreshing = false,
            errorMessage = errorMessage
        )
    }
}