package org.modeart.tailor.feature.onboarding.login.contract

import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route

sealed interface LoginScreenUiEffect {
    data class ShowRawNotification(val msg: String) : LoginScreenUiEffect

    sealed class Navigation(open val screen: Route) : LoginScreenUiEffect {
        data object Main : Navigation(
            screen = OnBoardingNavigation.login
        )

        data object SignUp : Navigation(
            screen = OnBoardingNavigation.signup
        )
    }
}
