package org.modeart.tailor.feature.main.addNewCustomer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.register_new_customer
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.common.MainToolbar
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiEffect
import org.modeart.tailor.feature.main.addNewCustomer.customSize.OverallSize
import org.modeart.tailor.feature.main.addNewCustomer.fastSize.FastSizeSelectionScreen
import org.modeart.tailor.feature.main.addNewCustomer.info.BasicInfo
import org.modeart.tailor.feature.main.addNewCustomer.info.FinalInfo
import org.modeart.tailor.feature.main.addNewCustomer.info.StyleFeatures
import org.modeart.tailor.feature.main.addNewCustomer.info.SupplementaryInformationScreen
import org.modeart.tailor.feature.main.measurments.MeasurementViewModel
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementSelectedCustomer
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Background

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun AddNewCustomerScene(onNavigate: (Route) -> Unit, onBack: () -> Unit) {
    val viewModel = koinViewModel(NewCustomerViewModel::class)
    val viewModelMeasurement = koinViewModel(MeasurementViewModel::class)

    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    val isRegisteringNewSize =
        viewModelMeasurement.state.value.customerType == MeasurementSelectedCustomer.OldCustomer
    var notification by remember { mutableStateOf<NewCustomerUiEffect.ShowRawNotification?>(null) }
    var localizedNotification by remember {
        mutableStateOf<NewCustomerUiEffect.ShowLocalizedNotification?>(
            null
        )
    }

    BackHandler {
        if (isRegisteringNewSize)
            onBack()
        else
            handleBackStack(state.step, viewModel, onBack)
    }


    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is NewCustomerUiEffect.Navigation -> onNavigate(effect.screen)
                is NewCustomerUiEffect.ShowRawNotification -> {
                    notification = effect
                }

                is NewCustomerUiEffect.ShowLocalizedNotification -> localizedNotification = effect
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }
    localizedNotification?.let { notif ->
        InAppNotification(message = stringResource(notif.msg), isError = notif.isError) {
            localizedNotification = null
        }
    }



    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        MainToolbar(stringResource(Res.string.register_new_customer)) {
            if (isRegisteringNewSize)
                onBack()
            else
                handleBackStack(state.step, viewModel, onBack)
        }
        when (state.step) {
            NewCustomerSteps.BasicInfo -> BasicInfo(state, viewModel)
            NewCustomerSteps.StyleFeature -> StyleFeatures(state, viewModel)
            NewCustomerSteps.SupplementaryInfo -> SupplementaryInformationScreen(state, viewModel)
            NewCustomerSteps.FinalInfo -> FinalInfo(state, viewModel)
            NewCustomerSteps.OverallSize -> OverallSize(isRegisteringNewSize, state, viewModel)
            NewCustomerSteps.FastSize -> FastSizeSelectionScreen(viewModel)
        }
    }
}

private fun handleBackStack(
    steps: NewCustomerSteps,
    viewModel: NewCustomerViewModel,
    onBack: () -> Unit
) {
    when (steps) {
        NewCustomerSteps.BasicInfo -> onBack()
        NewCustomerSteps.StyleFeature -> viewModel.updateStep(NewCustomerSteps.BasicInfo)
        NewCustomerSteps.SupplementaryInfo -> viewModel.updateStep(NewCustomerSteps.StyleFeature)
        NewCustomerSteps.FinalInfo -> viewModel.updateStep(NewCustomerSteps.SupplementaryInfo)
        NewCustomerSteps.OverallSize -> viewModel.updateStep(NewCustomerSteps.FinalInfo)
        NewCustomerSteps.FastSize -> viewModel.updateStep(NewCustomerSteps.FastSize)
    }
}
