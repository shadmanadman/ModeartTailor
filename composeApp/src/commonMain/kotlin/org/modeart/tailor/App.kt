package org.modeart.tailor

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.modeart.tailor.feature.main.addNewCustomer.AddNewCustomerScene
import org.modeart.tailor.feature.main.contact.ContactUsScene
import org.modeart.tailor.feature.main.customer.CustomerProfileScene
import org.modeart.tailor.feature.main.home.HomeScene
import org.modeart.tailor.feature.main.main.BottomNavScene
import org.modeart.tailor.feature.main.measurments.MeasurementScene
import org.modeart.tailor.feature.main.note.NewNoteScene
import org.modeart.tailor.feature.main.plans.PlansScene
import org.modeart.tailor.feature.main.profile.EditeProfileScene
import org.modeart.tailor.feature.main.profile.MainProfileScene
import org.modeart.tailor.feature.main.profile.PlanHistoryScene
import org.modeart.tailor.feature.onboarding.login.LoginScene
import org.modeart.tailor.feature.onboarding.signup.SignupScene
import org.modeart.tailor.feature.onboarding.splash.SplashScene
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

                scene(route = MainNavigation.home.fullPath) {
                    HomeScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                scene(route = MainNavigation.measure.fullPath) {
                    MeasurementScene(onNavigate = { navigator.navigate(it.fullPath) }, onBack = {navigator.popBackStack()})
                }
                scene(route = MainNavigation.newNote.fullPath) {
                    NewNoteScene(
                        onNavigate = { navigator.navigate(it.fullPath) },
                        onBack = { navigator.popBackStack() })
                }
                //************************ Profile *********************************//
                scene(route = MainNavigation.profile.fullPath) {
                    MainProfileScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                scene(route = MainNavigation.editProfile.fullPath) {
                    EditeProfileScene(onNavigate = { navigator.navigate(it.fullPath) })
                }
                scene(route = MainNavigation.contact.fullPath){
                    ContactUsScene()
                }
                scene(route = MainNavigation.planHistory.fullPath){
                    PlanHistoryScene(onBack = {navigator.popBackStack()})
                }
                scene(route = MainNavigation.plan.fullPath){
                    PlansScene()
                }
                //************************ Customer *********************************//
                scene(route = MainNavigation.newCustomer.fullPath) {
                    AddNewCustomerScene(
                        onNavigate = { navigator.navigate(it.fullPath) },
                        onBack = { navigator.popBackStack() })
                }
                scene(route = MainNavigation.customerProfile.fullPath){
                    CustomerProfileScene { navigator.navigate(it.fullPath) }
                }
            }
        }
    }

}