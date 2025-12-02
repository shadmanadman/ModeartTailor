package org.modeart.tailor.feature.main.customer

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.api.customer.CustomerService
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.customer.contract.CustomerUiState
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.feature.main.home.contract.HomeUiState
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.navigation.MainNavigation
import kotlin.toString

class CustomersViewModel(private val businessService: BusinessService): ViewModel() {
    private val _uiState = MutableStateFlow(CustomerUiState())

    val uiState: StateFlow<CustomerUiState> = _uiState

    var effects = Channel<CustomerUiEffect>(Channel.UNLIMITED)
        private set

    init {
        getBusinessCustomers()
    }
    fun navigateToProfile() {
        effects.trySend(CustomerUiEffect.Navigation(MainNavigation.profile))
    }

    fun navigateToSearch() {
        effects.trySend(CustomerUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun navigateToNavigation() {
        effects.trySend(CustomerUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun customerFilterChanged(filter: CustomerFilter){
        _uiState.update { it.copy(customerListFilter = filter) }
    }

    fun selectedCustomer(customer: CustomerProfile){
        _uiState.update { it.copy(selectedCustomer = customer) }
    }

    fun selectedSize(size: CustomerProfile.Size){
        _uiState.update { it.copy(selectedSize = size) }
    }

    fun searchQueryChanged(query: String){
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun getBusinessCustomers() {
        viewModelScope.launch {
            val response = businessService.getBusinessCustomers()
            when (response) {
                is ApiResult.Error -> effects.send(
                    CustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            businessCustomers = response.data
                        )
                    }
                }
            }
        }
    }

    fun searchForCustomers(){
        viewModelScope.launch {
            val response = businessService.searchCustomers(_uiState.value.searchQuery)
            when(response){
                is ApiResult.Error -> effects.send(
                    CustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    ))
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            businessCustomers = response.data
                        )
                    }
                }
            }
        }
    }
}