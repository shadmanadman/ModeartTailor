package org.modeart.tailor.koin

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.modeart.tailor.api.networkModule
import org.modeart.tailor.feature.main.mainModule
import org.modeart.tailor.feature.onboarding.onBoardingModule

fun appModule() = listOf(networkModule, onBoardingModule, mainModule)


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appModule()
        )
    }
}
