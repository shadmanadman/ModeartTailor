package org.modeart.tailor.feature.onboarding

import org.koin.dsl.module
import org.modeart.tailor.api.MainHttpClient
import org.modeart.tailor.api.TokenRepo
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.auth.OnBoardingRepo
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.feature.onboarding.login.LoginViewModel
import org.modeart.tailor.feature.onboarding.welcome.WelcomeViewModel
import org.modeart.tailor.prefs.PrefsDataStore
import org.modeart.tailor.prefs.rememberDataStore


fun onBoardingModule() = module {
    single { WelcomeViewModel() }
    single { LoginViewModel() }

    single<PrefsDataStore> { rememberDataStore() }
    single<TokenService> { TokenRepo(get()) }
    single { MainHttpClient(get())() }

    single<OnBoardingService> { OnBoardingRepo(client = get()) }
}