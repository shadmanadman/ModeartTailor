package org.modeart.tailor.feature.main

import org.koin.dsl.module
import org.modeart.tailor.api.MainHttpClient
import org.modeart.tailor.api.business.BusinessRepo
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.api.customer.CustomerRepo
import org.modeart.tailor.api.customer.CustomerService
import org.modeart.tailor.feature.main.main.BottomNavViewModel

fun mainModule() = module {
    single { BottomNavViewModel() }

    single<BusinessService> { BusinessRepo(client = MainHttpClient()()) }
    single<CustomerService> { CustomerRepo(client = MainHttpClient()()) }
}