package org.modeart.tailor.feature.onboarding.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.enter_code
import modearttailor.composeapp.generated.resources.login
import modearttailor.composeapp.generated.resources.login_title
import modearttailor.composeapp.generated.resources.logo
import modearttailor.composeapp.generated.resources.mobile_number
import modearttailor.composeapp.generated.resources.no_account_signup
import modearttailor.composeapp.generated.resources.vector_login
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiEffect
import org.modeart.tailor.feature.onboarding.login.contract.LoginScreenUiState
import org.modeart.tailor.feature.onboarding.login.contract.LoginStep
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScene(
    onNavigate: (Route) -> Unit
) {
    val viewModel = koinViewModel(LoginViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<LoginScreenUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is LoginScreenUiEffect.Navigation.Main -> onNavigate(effect.screen)
                is LoginScreenUiEffect.Navigation.SignUp -> onNavigate(effect.screen)
                is LoginScreenUiEffect.ShowRawNotification -> {
                    notification = effect
                }
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }
    LoginSceneContent(state, viewModel)
}

@Composable
fun LoginSceneContent(state: LoginScreenUiState, viewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Image(
            modifier = Modifier.align(Alignment.BottomStart),
            painter = painterResource(Res.drawable.vector_login),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(top = 130.dp),
                painter = painterResource(Res.drawable.logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f).padding(top = 16.dp),
                text = stringResource(Res.string.login_title),
                style = appTypography().title16.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            when (state.currentStep) {
                LoginStep.EnterPhoneNumber -> {
                    OutlinedTextFieldModeArt(
                        value = state.number,
                        hint = stringResource(Res.string.mobile_number),
                        onValueChange = viewModel::verifyPhoneNumber
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RoundedCornerButton(
                        isEnabled = state.enableContinue,
                        text = stringResource(Res.string.login),
                        onClick = viewModel::login
                    )
                }

                LoginStep.EnterVerificationCode -> {
                    OutlinedTextFieldModeArt(
                        value = state.code,
                        hint = stringResource(Res.string.enter_code),
                        onValueChange = viewModel::onCodeUpdated
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RoundedCornerButton(
                        isEnabled = state.enableContinue,
                        text = stringResource(Res.string.login),
                        onClick = viewModel::login
                    )
                }
            }

            Text(
                modifier = Modifier.weight(1f).padding(16.dp)
                    .clickable(indication = null, interactionSource = null){
                        viewModel.goToSignUp()
                    },
                text = stringResource(Res.string.no_account_signup),
                style = appTypography().title16.copy(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview
@Composable
fun LoginScenePreview() {
    //LoginSceneContent(state = LoginScreenUiState(), viewModel = LoginViewModel())
}

