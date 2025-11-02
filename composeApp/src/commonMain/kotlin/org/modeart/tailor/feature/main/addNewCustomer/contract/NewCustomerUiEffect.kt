package org.modeart.tailor.feature.main.addNewCustomer.contract

import org.jetbrains.compose.resources.StringResource
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.note.contract.NoteUiEffect
import org.modeart.tailor.navigation.Route

sealed interface NewCustomerUiEffect {
    data class ShowRawNotification(
        val msg: String,
        val isError: Boolean = true,
        val errorCode: String = ""
    ) :
        NewCustomerUiEffect
    data class ShowLocalizedNotification(val msg: StringResource, val errorCode: String = "",val isError: Boolean) :
        NewCustomerUiEffect


    data class Navigation(val screen: Route) : NewCustomerUiEffect
    data object NavigateBack: NewCustomerUiEffect
}