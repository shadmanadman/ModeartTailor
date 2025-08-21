package org.modeart.tailor.feature.onboarding.welcome

import org.modeart.tailor.feature.onboarding.welcome.contract.WelcomeScreenUiEffect
import WelcomeScreenUiState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


class WelcomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WelcomeScreenUiState())

    val uiState: StateFlow<WelcomeScreenUiState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = _uiState.value,
            )

    var effects = Channel<WelcomeScreenUiEffect>(Channel.UNLIMITED)
        private set

    fun onDone() {
        viewModelScope.launch {
            effects.send(WelcomeScreenUiEffect.Navigation.Login)
        }
    }
}