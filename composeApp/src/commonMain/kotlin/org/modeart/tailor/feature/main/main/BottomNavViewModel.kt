package org.modeart.tailor.feature.main.main

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.main.main.contract.BottomNavUiEffect
import org.modeart.tailor.feature.main.main.contract.BottomNavUiState
import org.modeart.tailor.feature.main.main.contract.RootBottomNavId

class BottomNavViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BottomNavUiState())

    val state: StateFlow<BottomNavUiState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = _uiState.value,
            )

    var effect = Channel<BottomNavUiEffect>(Channel.UNLIMITED)
        private set


    fun selectedScreen(screen: RootBottomNavId) {
        _uiState.update { it.copy(selectedScreen = screen) }
    }

    fun openHome() {
        viewModelScope.launch {
            effect.send(BottomNavUiEffect.Navigation.Home)
        }
    }

    fun openNote() {
        viewModelScope.launch { effect.send(BottomNavUiEffect.Navigation.Note) }
    }

    fun openMeasure() {
        viewModelScope.launch { effect.send(BottomNavUiEffect.Navigation.Measure) }
    }

    fun openCustomers() {
        viewModelScope.launch { effect.send(BottomNavUiEffect.Navigation.Customers) }
    }
}