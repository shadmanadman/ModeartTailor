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
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.LocalUserData
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.feature.onboarding.login.CODE_LENGTH
import org.modeart.tailor.feature.onboarding.signup.contract.SignupScreenUiEffect
import org.modeart.tailor.feature.onboarding.signup.contract.SignupScreenUiState
import org.modeart.tailor.feature.onboarding.signup.contract.SignupStep
import org.modeart.tailor.model.business.AuthRequest

class SignupViewModel(
    private val onBoardingService: OnBoardingService,
    private val tokenService: TokenService
) : ViewModel() {
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
            )
        }
    }

    fun onCodeUpdated(code: String) {
        _uiState.update { state ->
            state.copy(code = code, enableContinue = code.length == CODE_LENGTH)
        }
    }


    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentStage = _uiState.value.currentStep
            when (currentStage) {
                SignupStep.EnterPhoneNumber -> {
                    val response =
                        _uiState.value.run { onBoardingService.sendOtpTest(phoneNumber = number) }
                    when (response) {
                        is ApiResult.Error -> effects.send(
                            SignupScreenUiEffect.ShowRawNotification(
                                msg = response.message, errorCode = response.code.toString()
                            )
                        )

                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    currentStep = SignupStep.EnterVerificationCode,
                                    code = response.data.otp
                                )
                            }
                        }
                    }
                }

                SignupStep.EnterVerificationCode -> {
                    val response =
                        _uiState.value.run { onBoardingService.register(AuthRequest(number, code)) }
                    when (response) {
                        is ApiResult.Error ->
                            effects.send(
                                SignupScreenUiEffect.ShowRawNotification(
                                    msg = response.message, errorCode = response.code.toString()
                                )
                            )

                        is ApiResult.Success -> {
                            tokenService.saveToken(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken
                            )
                            tokenService.saveUserDataLocal(LocalUserData(phoneNumber = _uiState.value.number))
                            effects.send(SignupScreenUiEffect.Navigation.Main)
                        }
                    }
                }
            }
        }
    }
}