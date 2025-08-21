package org.modeart.tailor.feature.onboarding.signup.contract

enum class SignupStep {
    EnterPhoneNumber,
    EnterVerificationCode
}
data class SignupScreenUiState(
    val currentStep: SignupStep = SignupStep.EnterPhoneNumber,
    val code: String = "",
    val number: String = "",
    val isLoading: Boolean = false,
    val enableContinue: Boolean = false,
)