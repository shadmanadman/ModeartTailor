package org.modeart.tailor.feature.main.addNewCustomer.contract

import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.navigation.Route

sealed interface NewCustomerUiEffect {
    data class ShowRawNotification(
        val msg: String,
        val isError: Boolean = true,
        val errorCode: String = ""
    ) :
        NewCustomerUiEffect

    data class Navigation(val screen: Route) : NewCustomerUiEffect
}