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

    fun openScreen(screen: RootBottomNavId) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedScreen = screen) }
            when (screen) {
                RootBottomNavId.Home -> effect.send(BottomNavUiEffect.Navigation.Home)
                RootBottomNavId.Measure -> effect.send(BottomNavUiEffect.Navigation.Measure)
                RootBottomNavId.Note -> effect.send(BottomNavUiEffect.Navigation.Note)
                RootBottomNavId.Customer -> effect.send(BottomNavUiEffect.Navigation.Customers)
            }
        }

    }
}