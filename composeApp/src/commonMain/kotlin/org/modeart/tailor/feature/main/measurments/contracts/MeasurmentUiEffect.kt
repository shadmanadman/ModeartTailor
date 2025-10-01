package org.modeart.tailor.feature.main.measurments.contracts

import org.jetbrains.compose.resources.StringResource
import org.modeart.tailor.feature.main.main.contract.BottomNavUiEffect
import org.modeart.tailor.navigation.Route

sealed interface MeasurementUiEffect {
    data class ShowRawNotification(val msg: String) : MeasurementUiEffect
    data class ShowLocalizedNotification(val msg: StringResource) : MeasurementUiEffect

    sealed class Navigation(open val screen: Route) : MeasurementUiEffect
}