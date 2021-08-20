package com.javimartd.theguardian.v2.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javimartd.theguardian.v2.data.NewsDataSource
import com.javimartd.theguardian.v2.ui.NewsViewModel

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val newsDataSource: NewsDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}