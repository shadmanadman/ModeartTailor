package org.modeart.tailor.feature.main

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.modeart.tailor.api.TokenRepo
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.auth.OnBoardingRepo
import org.modeart.tailor.api.auth.OnBoardingService
import org.modeart.tailor.api.business.BusinessRepo
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.api.customer.CustomerRepo
import org.modeart.tailor.api.customer.CustomerService
import org.modeart.tailor.feature.main.customer.CustomersViewModel
import org.modeart.tailor.feature.main.home.HomeViewModel
import org.modeart.tailor.feature.main.main.BottomNavViewModel
import org.modeart.tailor.feature.main.note.NoteViewModel
import org.modeart.tailor.feature.main.profile.ProfileViewModel
import org.modeart.tailor.prefs.PrefsDataStore
import org.modeart.tailor.prefs.rememberDataStore

val mainModule = module {
    single { BottomNavViewModel() }
    single { HomeViewModel(get()) }
    single{ ProfileViewModel(get(),get()) }
    single { NoteViewModel(get()) }
    single { CustomersViewModel(get()) }
    singleOf(::BusinessRepo).bind<BusinessService>()
    singleOf(::OnBoardingRepo).bind<OnBoardingService>()
}