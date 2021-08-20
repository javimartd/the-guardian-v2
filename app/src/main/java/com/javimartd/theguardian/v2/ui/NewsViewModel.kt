package com.javimartd.theguardian.v2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.data.NewsDataSource
import com.javimartd.theguardian.v2.ui.model.Section
import com.javimartd.theguardian.v2.ui.state.NewsViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(private val newsDataSource: NewsDataSource): ViewModel() {

    companion object {
        private const val WORLD_NEWS_SECTION_ID = "world"
    }

    private val _content = MutableLiveData<NewsViewState>().apply {
        this.value = NewsViewState.Loading
    }
    val content: LiveData<NewsViewState>
        get() = _content

    private var sections = mutableListOf<Section>()

    init {
        fetchContent()
    }

    private fun fetchContent() {
        viewModelScope.launch {
            coroutineScope {
                val newsDeferred = async { newsDataSource.getNews(WORLD_NEWS_SECTION_ID) }
                val sectionsDeferred = async { newsDataSource.getSections() }
                val newsAwait = newsDeferred.await()
                val sectionsAwait = sectionsDeferred.await()

                sections.addAll(sectionsAwait)

                _content.value = NewsViewState.LoadData(
                    newsAwait,
                    sections.flatMap { listOf(it.webTitle) }
                )
            }
        }
    }

    fun fetchNews(sectionName: String) {
        val sectionId = sections.single { it.webTitle == sectionName }.id
        viewModelScope.launch {
            val news = newsDataSource.getNews(sectionId)
            _content.value = NewsViewState.ShowNews(news)
        }
    }
}