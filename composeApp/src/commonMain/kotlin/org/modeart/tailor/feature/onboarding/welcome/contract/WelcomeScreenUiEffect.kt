package org.modeart.tailor.feature.onboarding.welcome.contract

import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route

sealed interface WelcomeScreenUiEffect {
    data class ShowRawNotification(val msg: String) : WelcomeScreenUiEffect

    sealed class Navigation(open val screen: Route) : WelcomeScreenUiEffect {
        data object Login : Navigation(
            screen = OnBoardingNavigation.login
        )
        data object Main : Navigation(
            screen = MainNavigation.main
        )


        data object SignUp : Navigation(
            screen = OnBoardingNavigation.signup
        )
    }
}