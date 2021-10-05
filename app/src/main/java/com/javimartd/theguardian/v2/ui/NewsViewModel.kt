package com.javimartd.theguardian.v2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.state.Resource
import com.javimartd.theguardian.v2.ui.mapper.toNewsView
import com.javimartd.theguardian.v2.ui.mapper.toSectionsView
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
        val job = viewModelScope.launch {
            coroutineScope {
                val newsDeferred = async { repository.getNews(WORLD_NEWS_SECTION_ID) }
                val sectionsDeferred = async { repository.getSections() }
                val newsResponseAwait = newsDeferred.await()
                val sectionsResponseAwait = sectionsDeferred.await()

                if (
                    newsResponseAwait is Resource.Success &&
                    sectionsResponseAwait is Resource.Success
                ) {
                    _content.value = NewsViewState.LoadData(
                        newsResponseAwait.data.toNewsView(),
                        sectionsResponseAwait.data.toSectionsView().flatMap { listOf(it.webTitle) }
                    )
                } else {
                    _content.value = NewsViewState.Error
                }
            }
        }
        job.cancel()
    }

    fun fetchNews(sectionId: String) {
        viewModelScope.launch {
            when(val response = repository.getNews(sectionId)){
                is Resource.Success -> {
                    _content.value = NewsViewState.ShowNews(response.data.toNewsView())
                }
                is Resource.Error -> {
                    _content.value = NewsViewState.Error
                }
            }
        }
    }

    fun onSelectedSection(sectionName: String) {
        viewModelScope.launch {
            val response = repository.getSections()
            if (response is Resource.Success) {
                val sectionId = response.data
                    .toSectionsView()
                    .single { sectionName == it.webTitle }.id
                fetchNews(sectionId)
            }
        }
    }
}