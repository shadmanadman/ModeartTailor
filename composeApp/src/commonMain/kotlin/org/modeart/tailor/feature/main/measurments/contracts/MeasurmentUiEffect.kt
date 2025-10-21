package org.modeart.tailor.feature.main.measurments.contracts

import org.jetbrains.compose.resources.StringResource
import org.modeart.tailor.navigation.Route

sealed interface MeasurementUiEffect {
    data class ShowRawNotification(val msg: String) : MeasurementUiEffect
    data class ShowLocalizedNotification(val msg: StringResource) : MeasurementUiEffect

    data class Navigation(val screen: Route) : MeasurementUiEffect
    data object ShowSelectCustomerBottomSheet: MeasurementUiEffect
}