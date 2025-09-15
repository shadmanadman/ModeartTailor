package org.modeart.tailor.feature.onboarding.signup.contract

import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route

sealed interface SignupScreenUiEffect {
    data class ShowRawNotification(val msg: String = "", val errorCode: String = "") :
        SignupScreenUiEffect

    sealed class Navigation(open val screen: Route) : SignupScreenUiEffect {
        data object Main : Navigation(
            screen = OnBoardingNavigation.login
        )

        data object Login : Navigation(
            screen = OnBoardingNavigation.login
        )
    }
}