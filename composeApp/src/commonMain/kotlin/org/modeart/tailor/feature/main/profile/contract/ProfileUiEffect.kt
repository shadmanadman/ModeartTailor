package org.modeart.tailor.feature.main.profile.contract

import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiEffect
import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route

sealed interface ProfileUiEffect {
    data class ShowRawNotification(val msg: String, val errorCode: String = "") :
        ProfileUiEffect

    data class Navigation(val screen: Route) : ProfileUiEffect
}