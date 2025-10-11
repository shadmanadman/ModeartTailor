package org.modeart.tailor.feature.main.addNewCustomer

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
import org.modeart.tailor.api.customer.CustomerService
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiEffect
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.model.customer.CustomerBodyForm
import org.modeart.tailor.model.customer.CustomerColor
import org.modeart.tailor.model.customer.CustomerGender
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.model.customer.CustomerShoulder
import org.modeart.tailor.model.customer.CustomerSizeFreedom
import org.modeart.tailor.model.customer.CustomerSizeSource
import org.modeart.tailor.model.customer.CustomerStyle
import kotlin.toString

class NewCustomerViewModel(private val customerService: CustomerService) : ViewModel() {

    private val _uiState = MutableStateFlow(NewCustomerUiState())

    val uiState: StateFlow<NewCustomerUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<NewCustomerUiEffect>(Channel.UNLIMITED)
        private set

    private val selectedCustomer = MutableStateFlow<CustomerProfile?>(null)

    fun setSelectedCustomer(customer: CustomerProfile) {
        selectedCustomer.value = customer
    }

    fun updateStep(step: NewCustomerSteps) {
        _uiState.update { it.copy(step = step) }
    }

    fun basicInfoChanged(
        gender: CustomerGender,
        fullName: String,
        phoneNumber: String,
        birth: String,
        customerAvatar: String
    ) {

        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    gender = gender,
                    name = fullName,
                    phoneNumber = phoneNumber,
                    birthday = birth,
                    avatar = customerAvatar
                ),
                step = NewCustomerSteps.StyleFeature
            )
        }
    }

    fun styleInfoChanged(
        customerStyle: CustomerStyle,
        customerBodyType: CustomerBodyForm,
        customerShoulderType: CustomerShoulder,
        fabricSensitivity: String
    ) {
        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    customerStyle = customerStyle,
                    customerBodyType = customerBodyType,
                    customerShoulderType = customerShoulderType,
                    fabricSensitivity = fabricSensitivity
                ),
                step = NewCustomerSteps.SupplementaryInfo
            )
        }
    }

    fun supplementaryInfoChanged(
        customerColor: CustomerColor,
        isOldCustomer: Boolean,
        referredBy: String,
        overallNote: String
    ) {
        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    customerColor = customerColor,
                    isOldCustomer = isOldCustomer,
                    referredBy = referredBy,
                    overallNote = overallNote
                ),
                step = NewCustomerSteps.OverallSize
            )
        }
    }

    fun finalInfoChanged(
        sizeSource: CustomerSizeSource,
        sizeFreedom: CustomerSizeFreedom,
        extraPhoto: String,
        importantNote: String
    ) {
        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    sizeSource = sizeSource,
                    sizeFreedom = sizeFreedom,
                    extraPhoto = extraPhoto,
                    importantNote = importantNote
                )
            )
        }
        if (selectedCustomer.value == null)
            newCustomer()
        else
            selectedCustomer.value?.let {
                updateCustomer(it.id)
            }
    }

    fun upperSizeChanged(upperBodySizes: CustomerProfile.UpperBodySizes) {
        _uiState.update { it.copy(customer = it.customer.copy(upperBodySizes = upperBodySizes)) }
    }

    fun lowerSizeChanged(lowerBodySizes: CustomerProfile.LowerBodySizes) {
        _uiState.update { it.copy(customer = it.customer.copy(lowerBodySizes = lowerBodySizes)) }
    }

    fun sleevesSizeChanged(sleevesSizes: CustomerProfile.SleevesSizes) {
        _uiState.update { it.copy(customer = it.customer.copy(sleevesSizes = sleevesSizes)) }
    }


    fun updateCustomer(customerId: String) {
        viewModelScope.launch {
            val response = customerService.updateCustomer(_uiState.value.customer)
            when (response) {
                is ApiResult.Error -> effects.send(
                    NewCustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                }
            }
        }
    }

    fun newCustomer() {
        viewModelScope.launch {
            val response = customerService.createCustomer(_uiState.value.customer)
            when (response) {
                is ApiResult.Error -> effects.send(
                    NewCustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                }
            }
        }
    }
}