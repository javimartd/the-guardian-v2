package com.javimartd.theguardian.v2.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javimartd.navwizard.common.BaseContract
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
    uiState: BaseContract.UiState,
    events: BaseContract.Events,
    effects: BaseContract.Effects
> : ViewModel() {

    abstract fun initState(): uiState
    abstract fun handleEvents(events: events)

    private val _uiState = MutableStateFlow(initState())
    val uiState: StateFlow<uiState> = _uiState

    private val _effect = Channel<effects>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    protected fun sendEffect(effect: effects) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}