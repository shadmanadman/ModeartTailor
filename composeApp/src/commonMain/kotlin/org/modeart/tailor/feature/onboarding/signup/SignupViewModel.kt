package org.modeart.tailor.feature.onboarding.signup

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.onboarding.login.contract.LoginStep
import org.modeart.tailor.feature.onboarding.signup.contract.SignupScreenUiEffect
import org.modeart.tailor.feature.onboarding.signup.contract.SignupScreenUiState
import org.modeart.tailor.feature.onboarding.signup.contract.SignupStep

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupScreenUiState())

    val uiState: StateFlow<SignupScreenUiState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = _uiState.value,
            )

    var effects = Channel<SignupScreenUiEffect>(Channel.UNLIMITED)
        private set

    fun goToLogin() {
        viewModelScope.launch {
            effects.send(SignupScreenUiEffect.Navigation.Login)
        }
    }

    fun verifyPhoneNumber(phone: String) {
        val isValid = phone.length == 11
        _uiState.update {
            it.copy(
                number = phone,
                enableContinue = isValid,
                currentStep = SignupStep.EnterVerificationCode
            )
        }
    }
}