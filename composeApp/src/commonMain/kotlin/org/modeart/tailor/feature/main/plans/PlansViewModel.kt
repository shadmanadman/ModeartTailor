package org.modeart.tailor.feature.main.plans

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.main.plans.contract.PlanUiState
import org.modeart.tailor.feature.main.plans.contract.SubscriptionsStep
import org.modeart.tailor.model.business.PlanType

class PlansViewModel: moe.tlaster.precompose.viewmodel.ViewModel() {
    private val _uiState = MutableStateFlow(PlanUiState())

    val uiState: StateFlow<PlanUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    fun setSelectedPlan(planType: PlanType){
        _uiState.update { it.copy(selectedPlan = planType, currentStep = SubscriptionsStep.TypeDetails) }
    }
}