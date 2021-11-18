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
        fetchContent()
    }

    fun fetchContent() {
        viewModelScope.launch {
            coroutineScope {
                val newsDeferred = async { repository.getNews(WORLD_NEWS_SECTION_ID) }
                val sectionsDeferred = async { repository.getSections() }
                val newsResponseAwait = newsDeferred.await()
                val sectionsResponseAwait = sectionsDeferred.await()

                if (
                    newsResponseAwait is Resource.Success &&
                    sectionsResponseAwait is Resource.Success
                ) {
                    _content.value = NewsViewState.ShowNewsAndSections(
                        newsResponseAwait.data.newsMapToView(),
                        sectionsResponseAwait.data
                            .sectionsMapToView()
                            .flatMap { listOf(it.webTitle) }
                    )
                } else {
                    _content.value = NewsViewState.ShowGenericError
                }
            }
        }
    }

    fun fetchNews(sectionId: String) {
        viewModelScope.launch {
            when(val response = repository.getNews(sectionId)) {
                is Resource.Success -> {
                    _content.value = NewsViewState.ShowNews(response.data.newsMapToView())
                }
                is Resource.Error -> {
                    when (response.errorTypes) {
                        is ErrorTypes.RemoteErrors.Network -> {
                            _content.value = NewsViewState.ShowNetworkError
                        }
                        else -> {
                            _content.value = NewsViewState.ShowGenericError
                        }
                    }
                }
            }
        }
    }

    fun onSelectedSection(sectionName: String) {
        viewModelScope.launch {
            when(val response = repository.getSections()) {
                is Resource.Success -> {
                    val sectionId = getSectionId(
                        response.data.sectionsMapToView(),
                        sectionName
                    )
                    fetchNews(sectionId)
                }
                is Resource.Error -> {
                    _content.value = NewsViewState.ShowGenericError
                }
            }
        }
    }

    private fun getSectionId(
        sections: List<Section>,
        sectionName: String
    ): String {
        return sections.single { sectionName == it.webTitle }.id
    }
}