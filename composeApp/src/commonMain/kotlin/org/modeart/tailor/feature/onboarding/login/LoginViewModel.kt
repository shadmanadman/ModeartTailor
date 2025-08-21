package org.modeart.tailor.feature.onboarding.login

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiEffect
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiState
import org.modeart.tailor.feature.onboarding.login.contract.LoginStep

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())

    val uiState: StateFlow<LoginScreenUiState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = _uiState.value,
            )

    var effects = Channel<LoginScreenUiEffect>(Channel.UNLIMITED)
        private set


    fun goToSignUp() {
        viewModelScope.launch {
            effects.send(LoginScreenUiEffect.Navigation.SignUp)
        }
    }

    fun verifyPhoneNumber(phone: String) {
        val isValid = phone.length == 11
        _uiState.update {
            it.copy(
                number = phone,
                enableContinue = isValid,
                currentStep = LoginStep.EnterVerificationCode
            )
        }
    }
}