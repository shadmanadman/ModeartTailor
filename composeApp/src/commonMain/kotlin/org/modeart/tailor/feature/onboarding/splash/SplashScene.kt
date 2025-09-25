package org.modeart.tailor.feature.onboarding.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.app_name
import modearttailor.composeapp.generated.resources.app_subtitle
import modearttailor.composeapp.generated.resources.each_body_has_its_own_story
import modearttailor.composeapp.generated.resources.logo
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.feature.onboarding.signup.contract.SignupScreenUiEffect
import org.modeart.tailor.feature.onboarding.splash.contract.SplashScreenUiEffect
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.OnSecondary
import org.modeart.tailor.theme.appTypography
import org.modeart.tailor.theme.getArabicShinFont

@Composable
@Preview
fun SplashScene(onNavigate:(Route)-> Unit) {
    val viewModel = koinViewModel(SplashViewModel::class)
    val effects = viewModel.effects.receiveAsFlow()
    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is SplashScreenUiEffect.Navigation.Welcome  -> onNavigate(effect.screen)
                is SplashScreenUiEffect.Navigation.Main -> onNavigate(effect.screen)
                is SplashScreenUiEffect.ShowRawNotification ->{}
            }
        }.collect()
    }
    Box(
        Modifier.fillMaxSize().background(color = Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(Res.string.app_name),
                style = appTypography().title16.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.padding(top = 80.dp),
                text = stringResource(Res.string.each_body_has_its_own_story),
                style = appTypography().title18.copy(fontFamily = getArabicShinFont())
            )
            Text(
                stringResource(Res.string.app_subtitle),
                style = appTypography().body14.copy(OnSecondary)
            )
        }
    }
}