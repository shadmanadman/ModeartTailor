package org.modeart.tailor.feature.main.addNewCustomer

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiEffect
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerBodyForm
import org.modeart.tailor.model.customer.CustomerColor
import org.modeart.tailor.model.customer.CustomerGender
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.model.customer.CustomerShoulder
import org.modeart.tailor.model.customer.CustomerSizeFreedom
import org.modeart.tailor.model.customer.CustomerSizeSource
import org.modeart.tailor.model.customer.CustomerStyle

class NewCustomerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewCustomerUiState())

    val uiState: StateFlow<NewCustomerUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<NewCustomerUiEffect>(Channel.UNLIMITED)
        private set

    fun basicInfoChanged(
        gender: Int,
        fullName: String,
        phoneNumber: String,
        birth: String,
        customerAvatar: String
    ) {
        val customerGender = if (gender == 0)
            CustomerGender.MALE
        else
            CustomerGender.FEMALE

        _uiState.update {
            it.copy(
                customer = it.customer.copy(
                    gender = customerGender,
                    name = fullName,
                    phoneNumber = phoneNumber,
                    birthday = birth,
                    avatar = customerAvatar
                )
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
                )
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
                )
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
    }

    fun upperSizeChanged() {

    }

    fun lowerSizeChanged() {

    }
}