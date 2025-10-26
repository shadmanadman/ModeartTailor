package org.modeart.tailor.feature.main.profile

import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.updates_was_successfully
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiEffect
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.feature.main.profile.contract.ProfileUiState
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.PlanStatus
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.OnBoardingNavigation
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.toString

class ProfileViewModel(
    private val businessService: BusinessService,
    private val tokenService: TokenService
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())

    val uiState: StateFlow<ProfileUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<ProfileUiEffect>(Channel.UNLIMITED)
        private set

    init {
        getProfile()
        getBusinessCustomers()
    }

    fun navigateToEditProfile() {
        effects.trySend(ProfileUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun navigateToAbout() {
        effects.trySend(ProfileUiEffect.Navigation(MainNavigation.about))
    }

    fun navigateToContact() {
        effects.trySend(ProfileUiEffect.Navigation(MainNavigation.contact))
    }

    fun logout() {
        viewModelScope.launch {
            tokenService.logout()
            effects.trySend(ProfileUiEffect.Navigation(OnBoardingNavigation.login))
        }
    }

    fun onFullNameUpdated(fullName: String) {
        _uiState.update {
            it.copy(fullName = fullName)
        }
    }

    fun onBusinessNameUpdated(businessName: String) {
        _uiState.update {
            it.copy(businessName = businessName)
        }
    }

    fun onPhoneNumberUpdated(phoneNumber: String) {
        _uiState.update {
            it.copy(phone = phoneNumber)
        }
    }

    fun onBusinessAddressUpdated(businessAddress: String) {
        _uiState.update {
            it.copy(address = businessAddress)
        }
    }


    fun updateProfile() {
        if (_uiState.value.fullName.isEmpty() && _uiState.value.businessName.isEmpty())
            return

        viewModelScope.launch {
            val response = businessService.updateBusinessProfile(
                BusinessProfile(
                    phoneNumber = _uiState.value.phone,
                    fullName = _uiState.value.fullName,
                    businessName = _uiState.value.businessName,
                    city = _uiState.value.address,
                    profilePictureUrl = _uiState.value.avatar
                ),
                id = _uiState.value.id
            )


            when (response) {
                is ApiResult.Error -> effects.send(
                    ProfileUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    effects.send(ProfileUiEffect.ShowLocalizedNotification(msg = Res.string.updates_was_successfully))
                }
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            val response = businessService.businessProfile()
            when (response) {
                is ApiResult.Error -> effects.send(
                    ProfileUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            id = response.data.id,
                            fullName = response.data.fullName ?: "",
                            businessName = response.data.businessName ?: "",
                            phone = response.data.phoneNumber ?: "",
                            address = response.data.city ?: "",
                            avatar = response.data.profilePictureUrl ?: "",
                            plans = response.data.plan ?: emptyList(),
                            remainingPlanInDays = remainingDaysFromMillis(
                                response.data.plan?.first { it.planStatus == PlanStatus.ACTIVE }?.dateOfPurchase
                                    ?: 0L
                            )

                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun remainingDaysFromMillis(targetMillis: Long): Long {
        val now = Clock.System.now()
        val targetInstant = Instant.fromEpochMilliseconds(targetMillis)

        val duration = targetInstant - now
        return duration.toDouble(DurationUnit.DAYS).toLong()
    }

    fun getBusinessCustomers() {
        viewModelScope.launch {
            val response = businessService.getBusinessCustomers()
            when (response) {
                is ApiResult.Error -> effects.send(
                    ProfileUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            customerCount = response.data.size
                        )
                    }
                }
            }
        }
    }


    fun uploadImage(byteArray: ByteArray) {
        viewModelScope.launch {
            val response = businessService.uploadImage(byteArray)
            when (response) {
                is ApiResult.Error -> effects.send(
                    ProfileUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update { it.copy(avatar = response.data.url) }
                }
            }
        }
    }
}