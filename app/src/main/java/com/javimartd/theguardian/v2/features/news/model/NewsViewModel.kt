package com.javimartd.theguardian.v2.features.news.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.domain.news.usecases.GetNewsUseCase
import com.javimartd.theguardian.v2.domain.news.usecases.GetSectionsUseCase
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.features.news.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    getSectionsUseCase: GetSectionsUseCase
): ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val searchResults: StateFlow<List<SectionData>> = snapshotFlow { searchQuery }
        .combine(getSectionsUseCase.invoke()) { searchQuery, sections ->
            when {
                searchQuery.isNotEmpty() -> {
                    sections.filter { it.webTitle.contains(searchQuery, ignoreCase = true) }
                }
                else -> sections
            }
        }
            .catch { emptyList<SectionData>() }
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    var uiState by mutableStateOf<NewsUiContract.State>(NewsUiContract.State.Loading)
        private set

    init {
        onIntent(NewsUiContract.Intent.SearchQueryClick(""))
    }

    fun onIntent(intent: NewsUiContract.Intent) {
        when (intent) {
            is NewsUiContract.Intent.SearchQueryClick -> {
                getNews(intent.sectionId)
            }
            is NewsUiContract.Intent.SearchQueryChange -> {
                onSearchQueryChange(intent.newQuery)
            }
            is NewsUiContract.Intent.OnRefresh -> {
                // TODO
            }
        }
    }

    private fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun getNews(sectionName: String) {
        uiState = NewsUiContract.State.Loading
        // viewModeScope.launch { ... } operates in the main thread by default
        viewModelScope.launch {
            val response = getNewsUseCase(sectionName)
            response.fold(
                onSuccess = { data ->
                    uiState = if (data.isEmpty()) {
                        NewsUiContract.State.NoNews
                    } else {
                        NewsUiContract.State.News(data.map { it.toPresentation() })
                    }
                },
                onFailure = {
                    handleError(it)
                }
            )
        }
    }

    fun handleError(throwable: Throwable) {
        val errorMessage = when (throwable as ErrorTypes) {
            is ErrorTypes.RemoteErrors.Network -> R.string.network_error_message
            is ErrorTypes.RemoteErrors.Server -> R.string.server_error_message
            else -> R.string.generic_error_message
        }
        uiState = NewsUiContract.State.Error(errorMessage)
    }
}