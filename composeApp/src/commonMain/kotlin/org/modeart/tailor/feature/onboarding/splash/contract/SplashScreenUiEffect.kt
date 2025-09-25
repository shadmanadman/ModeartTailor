package org.modeart.tailor.feature.onboarding.splash.contract

import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route

sealed interface SplashScreenUiEffect {
    data class ShowRawNotification(val msg: String = "", val errorCode: String = "") :
        SplashScreenUiEffect

    sealed class Navigation(open val screen: Route) : SplashScreenUiEffect {
        data object Main : Navigation(
            screen = MainNavigation.main
        )

        data object Welcome : Navigation(
            screen = OnBoardingNavigation.welcome
        )
    }
}