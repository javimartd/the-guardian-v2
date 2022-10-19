package com.javimartd.theguardian.v2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.domain.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.ui.mapper.toPresentation
import com.javimartd.theguardian.v2.ui.model.NewsUiState
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

    private val _uiState = MutableLiveData<NewsUiState>().apply {
        this.value = NewsUiState.Loading
    }
    val uiState: LiveData<NewsUiState>
        get() = _uiState

    init {
        getAll()
    }

    fun onEvent(event: NewsUiEvent) {
        when (event) {
            is NewsUiEvent.SectionSelected -> {
                getNews(event.sectionName)
            }
        }
    }

    fun getAll() {
        viewModelScope.launch {
            coroutineScope {
                val deferredNews = async { getNewsUseCase.invoke("") }
                val deferredSections = async { getSectionsUseCase.invoke() }
                val newsResponse = deferredNews.await()
                val sectionsResponse = deferredSections.await()
                newsResponse.fold(
                    onSuccess = { data ->
                        _uiState.value = NewsUiState.ShowNews(data.map { it.toPresentation() })
                    },
                    onFailure = {
                        handleError(it)
                    }
                )
                sectionsResponse.fold(
                    onSuccess = { data ->
                        _uiState.value = NewsUiState.ShowSections(data.map { it.webTitle})
                    },
                    onFailure = {
                        handleError(it)
                    }
                )
            }
        }
    }

    fun getNews(sectionName: String) {
        viewModelScope.launch {
            val response = getNewsUseCase(sectionName)
            response.fold(
                onSuccess = { data ->
                    _uiState.value = NewsUiState.ShowNews(data.map { it.toPresentation() })
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
        _uiState.value = NewsUiState.ShowError(errorMessage)
    }
}