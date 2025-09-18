package org.modeart.tailor.feature.main.home.contract

import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.navigation.Route

sealed interface HomeUiEffect {
    data class ShowRawNotification(
        val msg: String,
        val isError: Boolean = true,
        val errorCode: String = ""
    ) :
        HomeUiEffect

    data class Navigation(val screen: Route) : HomeUiEffect
}