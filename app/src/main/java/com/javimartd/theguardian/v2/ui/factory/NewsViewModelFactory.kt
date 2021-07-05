package com.javimartd.theguardian.v2.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javimartd.theguardian.v2.data.TheGuardianApi
import com.javimartd.theguardian.v2.ui.NewsViewModel

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val theGuardianApi: TheGuardianApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(theGuardianApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}