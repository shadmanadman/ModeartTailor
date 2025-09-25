package org.modeart.tailor.feature.onboarding.splash

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.feature.onboarding.splash.contract.SplashScreenUiEffect

class SplashViewModel(private val tokenService: TokenService) : ViewModel() {

    var effects = Channel<SplashScreenUiEffect>(Channel.UNLIMITED)
        private set

    init {
        viewModelScope.launch {
            delay(3000)
            if (tokenService.isLoggedIn())
                effects.send(SplashScreenUiEffect.Navigation.Main)
            else
                effects.send(SplashScreenUiEffect.Navigation.Welcome)
        }
    }
}