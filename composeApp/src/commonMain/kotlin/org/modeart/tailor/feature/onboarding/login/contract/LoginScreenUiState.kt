package org.modeart.tailor.feature.onboarding.login.contract


enum class LoginStep {
    EnterPhoneNumber,
    EnterVerificationCode
}
data class LoginScreenUiState(
    val currentStep: LoginStep = LoginStep.EnterPhoneNumber,
    val code: String = "",
    val number: String = "",
    val isLoading: Boolean = false,
    val enableContinue: Boolean = false,
)
