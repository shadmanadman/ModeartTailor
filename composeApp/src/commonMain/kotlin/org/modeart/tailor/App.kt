package org.modeart.tailor

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import onBoardingModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
internal fun App() {
    KoinApplication(application = { modules(onBoardingModule()) }) {
        PreComposeApp {
        }
    }

}