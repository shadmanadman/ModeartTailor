package org.modeart.tailor

import androidx.compose.ui.window.ComposeUIViewController
import org.modeart.tailor.koin.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) {
    App()
}