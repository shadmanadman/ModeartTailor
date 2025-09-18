package org.modeart.tailor.feature.main.customer.contract

import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.navigation.Route

sealed interface CustomerUiEffect {
    data class ShowRawNotification(
        val msg: String,
        val isError: Boolean = true,
        val errorCode: String = ""
    ) :
        CustomerUiEffect

    data class Navigation(val screen: Route) : CustomerUiEffect
}