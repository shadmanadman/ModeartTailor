package org.modeart.tailor.feature.onboarding

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.modeart.tailor.api.auth.OnBoardingRepo
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.feature.onboarding.login.LoginViewModel
import org.modeart.tailor.feature.onboarding.signup.SignupViewModel
import org.modeart.tailor.feature.onboarding.welcome.WelcomeViewModel


val onBoardingModule = module {
    factory { WelcomeViewModel() }
    factory { LoginViewModel(get()) }
    factory { SignupViewModel(get()) }


    singleOf(::OnBoardingRepo).bind<OnBoardingService>()
}