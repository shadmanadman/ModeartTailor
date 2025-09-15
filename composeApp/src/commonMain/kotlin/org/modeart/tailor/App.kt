package org.modeart.tailor

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.modeart.tailor.feature.main.main.BottomNavScene
import org.modeart.tailor.feature.onboarding.login.LoginScene
import org.modeart.tailor.feature.onboarding.signup.SignupScene
import org.modeart.tailor.feature.onboarding.welcome.WelcomeScene
import org.modeart.tailor.koin.appModule
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.OnBoardingNavigation

@Composable
@Preview
internal fun App() {
    KoinApplication(application = { modules(modules = appModule()) }) {
        PreComposeApp {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = "welcome"
            ) {
                //********************** OnBoarding *********************************//
                // Welcome
                scene(route = OnBoardingNavigation.welcome.fullPath) {
                    WelcomeScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                // Login
                scene(route = OnBoardingNavigation.login.fullPath) {
                    LoginScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                // Signup
                scene(route = OnBoardingNavigation.signup.fullPath) {
                    SignupScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                //************************ Main *********************************//
                scene(route = MainNavigation.main.fullPath) {
                    BottomNavScene(onNavigate = { navigator.navigate(it.fullPath) })
                }

            }
        }
    }

}