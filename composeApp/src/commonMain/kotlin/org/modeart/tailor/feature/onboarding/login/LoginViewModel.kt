package org.modeart.tailor.feature.onboarding.login

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.phone_dos_not_exsits_signup
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.LocalUserData
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiEffect
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiState
import org.modeart.tailor.feature.onboarding.login.contract.LoginStep
import org.modeart.tailor.model.business.AuthRequest
import org.modeart.tailor.model.business.PhoneCheckRequest

const val CODE_LENGTH = 5

class LoginViewModel(
    private val onBoardingService: OnBoardingService,
    private val tokenService: TokenService
) : ViewModel() {
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
            )
        }
    }

    fun onCodeUpdated(code: String) {
        _uiState.update { state ->
            state.copy(code = code, enableContinue = code.length == CODE_LENGTH)
        }
    }


    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentStage = _uiState.value.currentStep
            when (currentStage) {
                LoginStep.EnterPhoneNumber -> {
                    val checkPhoneResponse = _uiState.value.run {
                        onBoardingService.checkPhoneNumber(
                            PhoneCheckRequest(number)
                        )
                    }
                    if (checkPhoneResponse is ApiResult.Success && checkPhoneResponse.data.exists) {
                        val response =
                            _uiState.value.run { onBoardingService.sendOtpTest(phoneNumber = number) }
                        when (response) {
                            is ApiResult.Error ->
                                effects.send(
                                    LoginScreenUiEffect.ShowRawNotification(
                                        msg = response.message, errorCode = response.code.toString()
                                    )
                                )

                            is ApiResult.Success -> {
                                _uiState.update {
                                    it.copy(
                                        currentStep = LoginStep.EnterVerificationCode,
                                        enableContinue = true,
                                        code = response.data.otp
                                    )
                                }
                            }
                        }
                    } else
                        effects.send(LoginScreenUiEffect.ShowLocalizedNotification(msg = Res.string.phone_dos_not_exsits_signup))
                }

                LoginStep.EnterVerificationCode -> {
                    val response =
                        _uiState.value.run { onBoardingService.login(AuthRequest(number, code)) }
                    when (response) {
                        is ApiResult.Error -> effects.send(
                            LoginScreenUiEffect.ShowRawNotification(
                                msg = response.message, errorCode = response.code.toString()
                            )
                        )

                        is ApiResult.Success -> {
                            tokenService.saveToken(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken
                            )
                            tokenService.saveUserDataLocal(LocalUserData(phoneNumber = _uiState.value.number))
                            effects.send(LoginScreenUiEffect.Navigation.Main)
                        }
                    }
                }
            }
        }
    }
}