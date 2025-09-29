package org.modeart.tailor.feature.main.main.contract

import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route

sealed interface BottomNavUiEffect {
    data class ShowRawNotification(val msg: String) : BottomNavUiEffect

    sealed class Navigation(open val screen: Route) : BottomNavUiEffect {
        data object Home : Navigation(
            screen = MainNavigation.home
        )
        data object Profile : Navigation(
            screen = MainNavigation.profile
        )


        data object Measure : Navigation(
            screen = MainNavigation.measure
        )

        data object Note : Navigation(
            screen = MainNavigation.note
        )

        data object Customers : Navigation(
            screen = MainNavigation.customers
        )
    }
}