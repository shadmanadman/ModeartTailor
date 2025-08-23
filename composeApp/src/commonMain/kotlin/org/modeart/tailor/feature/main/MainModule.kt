package org.modeart.tailor.feature.main

import org.koin.dsl.module
import org.modeart.tailor.feature.main.main.BottomNavViewModel

fun mainModule() = module {
    single { BottomNavViewModel() }
}