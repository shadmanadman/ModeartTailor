package org.modeart.tailor

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.modeart.tailor.koin.initKoin

class AppStartup : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AppStartup)
            androidLogger()
            modules()
        }
    }
}