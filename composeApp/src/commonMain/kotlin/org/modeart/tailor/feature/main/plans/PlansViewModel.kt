package org.modeart.tailor.feature.main.plans

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.updates_was_successfully
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.feature.main.plans.contract.PlanUiEffect
import org.modeart.tailor.feature.main.plans.contract.PlanUiState
import org.modeart.tailor.feature.main.plans.contract.SubscriptionsStep
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.BuyPlanRequest
import org.modeart.tailor.model.business.PlanType
import kotlin.toString

class PlansViewModel(private val businessService: BusinessService,): moe.tlaster.precompose.viewmodel.ViewModel() {
    private val _uiState = MutableStateFlow(PlanUiState())

    val uiState: StateFlow<PlanUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )
    var effects = Channel<PlanUiEffect>(Channel.UNLIMITED)
        private set

    fun setSelectedPlan(planType: PlanType){
        _uiState.update { it.copy(selectedPlan = planType, currentStep = SubscriptionsStep.TypeDetails) }
    }

    fun generatePaymentUrl() {

        viewModelScope.launch {
            val response = businessService.createPaymentUrl(BuyPlanRequest(_uiState.value.selectedPlan))


            when (response) {
                is ApiResult.Error -> effects.send(
                    PlanUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    effects.send(PlanUiEffect.SendToPayment(response.data.url))
                }
            }
        }
    }
}