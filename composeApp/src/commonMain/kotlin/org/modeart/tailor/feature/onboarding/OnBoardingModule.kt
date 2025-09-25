package org.modeart.tailor.feature.onboarding

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.modeart.tailor.api.auth.OnBoardingRepo
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.feature.onboarding.login.LoginViewModel
import org.modeart.tailor.feature.onboarding.signup.SignupViewModel
import org.modeart.tailor.feature.onboarding.splash.SplashViewModel
import org.modeart.tailor.feature.onboarding.welcome.WelcomeViewModel


val onBoardingModule = module {
    factory { WelcomeViewModel(get()) }
    factory { LoginViewModel(get(), get()) }
    factory { SignupViewModel(get(), get()) }
    single { SplashViewModel(get()) }

    singleOf(::OnBoardingRepo).bind<OnBoardingService>()
}