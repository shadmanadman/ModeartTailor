package org.modeart.tailor.feature.onboarding

import org.koin.dsl.module
import org.modeart.tailor.feature.onboarding.login.LoginViewModel
import org.modeart.tailor.feature.onboarding.welcome.WelcomeViewModel


fun onBoardingModule() = module {
    single { WelcomeViewModel() }
    single { LoginViewModel() }

}