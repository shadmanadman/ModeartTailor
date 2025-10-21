package org.modeart.tailor.feature.main.measurments

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementSelectedCustomer
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementStage
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementType
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementUiEffect
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementUiState
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.navigation.MainNavigation

class MeasurementViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MeasurementUiState())

    val state: StateFlow<MeasurementUiState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = _uiState.value,
            )

    var effect = Channel<MeasurementUiEffect>(Channel.UNLIMITED)
        private set


    fun measurementStageChanged(measurementStage: MeasurementStage){
        _uiState.update { it.copy(currentStage = measurementStage) }
    }

    fun customerTypeSelected(customerType: MeasurementSelectedCustomer){
        _uiState.update { it.copy(customerType = customerType) }
        if (customerType == MeasurementSelectedCustomer.OldCustomer)
            effect.trySend(MeasurementUiEffect.ShowSelectCustomerBottomSheet)
    }

    fun measurementTypeSelected(measurementType: MeasurementType){
        _uiState.update { it.copy(measurementType = measurementType) }
        if (measurementType== MeasurementType.CustomSize)
            effect.trySend(MeasurementUiEffect.Navigation(MainNavigation.newCustomer))
    }

}