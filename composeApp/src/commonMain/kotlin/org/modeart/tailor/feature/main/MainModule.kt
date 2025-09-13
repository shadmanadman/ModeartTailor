package org.modeart.tailor.feature.main

import org.koin.dsl.module
import org.modeart.tailor.api.TokenRepo
import org.modeart.tailor.api.TokenService
import org.modeart.tailor.api.business.BusinessRepo
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.api.customer.CustomerRepo
import org.modeart.tailor.api.customer.CustomerService
import org.modeart.tailor.feature.main.main.BottomNavViewModel
import org.modeart.tailor.prefs.PrefsDataStore
import org.modeart.tailor.prefs.rememberDataStore

val mainModule = module {
    single { BottomNavViewModel() }

    single<PrefsDataStore> { rememberDataStore() }
    single<TokenService> { TokenRepo(get(), get()) }
    single<PrefsDataStore> { rememberDataStore() }

    single<BusinessService> { BusinessRepo(client = get()) }
    single<CustomerService> { CustomerRepo(client = get()) }
}