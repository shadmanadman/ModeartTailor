package org.modeart.tailor.feature.main.home

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
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.feature.main.home.contract.HomeUiState
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.navigation.MainNavigation
import kotlin.toString

class HomeViewModel(private val businessService: BusinessService) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<HomeUiEffect>(Channel.UNLIMITED)
        private set

    init {
        getProfile()
        getBusinessCustomers()
    }
    fun navigateToProfile() {
        effects.trySend(HomeUiEffect.Navigation(MainNavigation.profile))
    }

    fun navigateToSearch() {
        effects.trySend(HomeUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun navigateToNotes() {
        effects.trySend(HomeUiEffect.Navigation(MainNavigation.notes))
    }

    fun getProfile() {
        viewModelScope.launch {
            val response = businessService.businessProfile()
            when (response) {
                is ApiResult.Error -> effects.send(
                    HomeUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            fullName = response.data.fullName.toString(),
                            phoneNumber = response.data.phoneNumber.toString(),
                            avatar = response.data.profilePictureUrl.toString(),
                            latestNotes = response.data.notes ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getBusinessCustomers() {
        viewModelScope.launch {
            val response = businessService.getBusinessCustomers("")
            when (response) {
                is ApiResult.Error -> effects.send(
                    HomeUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            latestCustomers = response.data,
                            customerCount = response.data.size.toString()
                        )
                    }
                }
            }
        }
    }
}