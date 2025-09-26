package org.modeart.tailor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.modeart.tailor.feature.main.main.BottomNavScene
import org.modeart.tailor.feature.main.profile.EditeProfileScene
import org.modeart.tailor.feature.main.profile.MainProfileScene
import org.modeart.tailor.feature.onboarding.login.LoginScene
import org.modeart.tailor.feature.onboarding.signup.SignupScene
import org.modeart.tailor.feature.onboarding.splash.SplashScene
import org.modeart.tailor.feature.onboarding.welcome.WelcomeScene
import org.modeart.tailor.feature.onboarding.welcome.WelcomeViewModel
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
                initialRoute = OnBoardingNavigation.splash.fullPath
            ) {
                //********************** OnBoarding *********************************//
                // Splash
                scene(route = OnBoardingNavigation.splash.fullPath) {
                    SplashScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
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

                //************************ Profile *********************************//
                scene(route = MainNavigation.profile.fullPath) {
                    MainProfileScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                scene(route = MainNavigation.editProfile.fullPath) {
                    EditeProfileScene(onNavigate = { navigator.navigate(it.fullPath) })
                }

            }
        }
    }

}