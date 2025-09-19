package org.modeart.tailor.feature.main.addNewCustomer

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiEffect
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState

class NewCustomerViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(NewCustomerUiState())

    val uiState: StateFlow<NewCustomerUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<NewCustomerUiEffect>(Channel.UNLIMITED)
        private set
}