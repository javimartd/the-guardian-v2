package com.javimartd.theguardian.v2.features.settings.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.theguardian.v2.data.repository.NewsRepositoryImpl
import com.javimartd.theguardian.v2.domain.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        longTaskInBackground()
    }

    fun longTaskInBackground() {
        viewModelScope.launch {
            Log.i(SettingsViewModel::class.java.name, "Task in Background")
            repository.longTaskInBackground()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(SettingsViewModel::class.java.name, "OnCleared")
    }
}