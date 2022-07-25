package com.javimartd.theguardian.v2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.NewsRepository
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Result
import com.javimartd.theguardian.v2.ui.mapper.newsMapToView
import com.javimartd.theguardian.v2.ui.mapper.sectionsMapToView
import com.javimartd.theguardian.v2.ui.model.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    companion object {
        private const val WORLD_NEWS_SECTION_ID = "world"
    }

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
                val deferredNews = async { repository.getNews(WORLD_NEWS_SECTION_ID) }
                val deferredSections = async { repository.getSections() }
                val newsResponse = deferredNews.await()
                val sectionsResponse = deferredSections.await()
                when (newsResponse) {
                    is Result.Success -> {
                        _uiState.value = NewsUiState.ShowNews(newsResponse.data.newsMapToView())
                    }
                    is Result.Error -> {
                        handleError(newsResponse.error)
                    }
                }
                when (sectionsResponse) {
                    is Result.Success -> {
                        _uiState.value = NewsUiState.ShowSections(
                            sectionsResponse.data.sectionsMapToView()
                        )
                    }
                    is Result.Error -> {
                        handleError(sectionsResponse.error)
                    }
                }
            }
        }
    }

    fun getNews(sectionName: String) {
        viewModelScope.launch {
            when(val response = repository.getNews(sectionName)) {
                is Result.Success -> {
                    _uiState.value = NewsUiState.ShowNews(response.data.newsMapToView())
                }
                is Result.Error -> handleError(response.error)
            }
        }
    }

    private fun handleError(error: ErrorTypes) {
        when (error) {
            is ErrorTypes.RemoteErrors.Network -> {
                _uiState.value = NewsUiState.ShowError(R.string.network_error_message)
            }
            is ErrorTypes.RemoteErrors.Server -> {
                _uiState.value = NewsUiState.ShowError(R.string.server_error_message)
            }
            else -> {
                _uiState.value = NewsUiState.ShowError(R.string.generic_error_message)
            }
        }
    }
}