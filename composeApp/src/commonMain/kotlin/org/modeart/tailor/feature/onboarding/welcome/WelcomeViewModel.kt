package org.modeart.tailor.feature.onboarding.welcome

import org.modeart.tailor.feature.onboarding.welcome.contract.WelcomeScreenUiEffect
import WelcomeScreenUiState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.navigation.MainNavigation


class WelcomeViewModel(private val tokenService: TokenService) : ViewModel() {
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


    suspend fun isLoggedIn(): Boolean {
        return tokenService.isLoggedIn()
    }

    init {
        viewModelScope.launch {
            if (isLoggedIn()) {
                _uiState.update { it.copy(initialRoute = MainNavigation.main.fullPath) }
            }
        }
    }

    fun onDone() {
        viewModelScope.launch {
            effects.send(WelcomeScreenUiEffect.Navigation.Login)
        }
    }
}