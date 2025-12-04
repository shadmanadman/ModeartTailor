package org.modeart.tailor.feature.main.plans.contract

import org.jetbrains.compose.resources.StringResource
import org.modeart.tailor.navigation.Route

sealed interface PlanUiEffect {
    data class ShowRawNotification(val msg: String, val errorCode: String = "") :
        PlanUiEffect
    data class ShowLocalizedNotification(val msg: StringResource, val errorCode: String = "") :
        PlanUiEffect
    data class SendToPayment(val url: String) : PlanUiEffect

    data class Navigation(val screen: Route) : PlanUiEffect
}