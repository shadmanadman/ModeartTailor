package org.modeart.tailor

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import onBoardingModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.modeart.tailor.feature.onboarding.welcome.WelcomeScene
import org.modeart.tailor.navigation.OnBoardingNavigation

@Composable
@Preview
internal fun App() {
    KoinApplication(application = { modules(onBoardingModule()) }) {
        PreComposeApp {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = "welcome"
            ) {
                scene(route = OnBoardingNavigation.welcome.fullPath) {
                    WelcomeScene(goToMain = {}, goToLogin = {}, goToSignup = {})
                }
            }
        }
    }

}