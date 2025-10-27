package org.modeart.tailor.feature.main.addNewCustomer

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.customer_saved_successfully
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.business.BusinessService
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

class NewCustomerViewModel(
    private val customerService: CustomerService,
    private val businessService: BusinessService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewCustomerUiState())

    val uiState: StateFlow<NewCustomerUiState> = _uiState

    var effects = Channel<NewCustomerUiEffect>(Channel.UNLIMITED)
        private set

    private val extraPhotos: MutableList<String> = mutableListOf()

    fun removeExtraPhoto(index: Int) {
        if (index <= extraPhotos.size)
            extraPhotos.removeAt(index)
    }

    fun setSelectedCustomer(customer: CustomerProfile) {
        _uiState.update { it.copy(customer = customer) }
    }


    fun fastSizeSelected(size: Int) {
        _uiState.update { it.copy(selectedFastSize = size) }
    }

    fun updateStep(step: NewCustomerSteps) {
        _uiState.update { it.copy(step = step) }
    }

    fun basicInfoChanged(
        gender: CustomerGender,
        fullName: String,
        phoneNumber: String,
        birth: String,
    ) {
        updateStep(NewCustomerSteps.StyleFeature)
        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    gender = gender,
                    name = fullName,
                    phoneNumber = phoneNumber,
                    age = birth,
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
        importantNote: String
    ) {
        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    sizeSource = sizeSource,
                    sizeFreedom = sizeFreedom,
                    extraPhoto = extraPhotos,
                    importantNote = importantNote
                )
            )
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

    fun saveCustomer() {
        if (_uiState.value.customer.id.isNotEmpty())
            updateCustomer()
        else
            newCustomer()
    }

    fun updateCustomer() {
        viewModelScope.launch {
            val response = customerService.updateCustomer(_uiState.value.customer)
            when (response) {
                is ApiResult.Error -> effects.send(
                    NewCustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    effects.send(
                        NewCustomerUiEffect.ShowLocalizedNotification(
                            msg = Res.string.customer_saved_successfully, isError = false
                        )
                    )
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
                    effects.send(
                        NewCustomerUiEffect.ShowLocalizedNotification(
                            msg = Res.string.customer_saved_successfully, isError = false
                        )
                    )
                }
            }
        }
    }

    fun uploadImage(isAvatar: Boolean, byteArray: ByteArray) {
        viewModelScope.launch {
            val response = businessService.uploadImage(byteArray)
            when (response) {
                is ApiResult.Error -> effects.send(
                    NewCustomerUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    if (isAvatar)
                        _uiState.update { it.copy(customer = it.customer.copy(avatar = response.data.url)) }
                    else {
                        extraPhotos.add(response.data.url)
                    }
                }
            }
        }
    }

}