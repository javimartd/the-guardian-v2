package com.javimartd.theguardian.v2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Resource
import com.javimartd.theguardian.v2.ui.mapper.newsMapToView
import com.javimartd.theguardian.v2.ui.mapper.sectionsMapToView
import com.javimartd.theguardian.v2.ui.model.Section
import com.javimartd.theguardian.v2.ui.state.NewsViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository): ViewModel() {

    companion object {
        private const val WORLD_NEWS_SECTION_ID = "world"
    }

    private val _content = MutableLiveData<NewsViewState>().apply {
        this.value = NewsViewState.Loading
    }
    val content: LiveData<NewsViewState>
        get() = _content

    init {
        getNewsAndSections()
    }

    fun getNewsAndSections() {
        viewModelScope.launch {
            coroutineScope {
                val deferredNews = async { repository.getNews(WORLD_NEWS_SECTION_ID) }
                val deferredSections = async { repository.getSections() }
                val newsResponse = deferredNews.await()
                val sectionsResponse = deferredSections.await()
                if (
                    newsResponse is Resource.Success &&
                    sectionsResponse is Resource.Success
                ) {
                    val news = newsResponse.data.newsMapToView()
                    val sections = getWebTitles(sectionsResponse.data.sectionsMapToView())
                    _content.value = NewsViewState.ShowNewsAndSections(news, sections)
                } else {
                    if (newsResponse is Resource.Error) {
                        handleError(newsResponse.error)
                    }
                    if (sectionsResponse is Resource.Error) {
                        handleError(sectionsResponse.error)
                    }
                }
            }
        }
    }

    fun getNews(sectionId: String) {
        viewModelScope.launch {
            when(val response = repository.getNews(sectionId)) {
                is Resource.Success -> {
                    _content.value = NewsViewState.ShowNews(response.data.newsMapToView())
                }
                is Resource.Error -> handleError(response.error)
            }
        }
    }

    fun onSectionSelected(sectionName: String) {
        viewModelScope.launch {
            when(val response = repository.getSections()) {
                is Resource.Success -> {
                    val sectionId = getSectionId(
                        response.data.sectionsMapToView(),
                        sectionName
                    )
                    getNews(sectionId)
                }
                is Resource.Error -> handleError(response.error)
            }
        }
    }

    private fun getSectionId(
        sections: List<Section>,
        sectionName: String
    ): String {
        return sections.single { sectionName == it.webTitle }.id
    }

    private fun getWebTitles(sections: List<Section>): List<String> {
        return sections.flatMap { listOf(it.webTitle) }
    }

    private fun handleError(error: ErrorTypes) {
        when (error) {
            is ErrorTypes.RemoteErrors.Network -> {
                _content.value = NewsViewState.ShowNetworkError
            }
            is ErrorTypes.RemoteErrors.Server -> {
                _content.value = NewsViewState.ShowServerError
            }
            is ErrorTypes.RemoteErrors.AccessDenied -> {
                _content.value = NewsViewState.ShowAccessDeniedError
            }
            else -> {
                _content.value = NewsViewState.ShowGenericError
            }
        }
    }
}