package org.modeart.tailor.feature.main.plans.contract

import org.modeart.tailor.model.business.PlanType

enum class SubscriptionsStep { SelectType, TypeDetails }
data class PlanUiState(
    val selectedPlan: PlanType = PlanType.MONTHLY,
    val currentStep: SubscriptionsStep = SubscriptionsStep.SelectType
)
