package org.modeart.tailor.feature.main.addNewCustomer.customSize

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.next
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerSizeFreedom
import org.modeart.tailor.model.customer.CustomerSizeSource
import org.modeart.tailor.theme.Background

enum class BodyPart {
    UPPER,
    LOWER,
    SLEEVES,
}

@Preview
@Composable
fun OverallSize(state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    val selectedBodyParts = remember { mutableStateOf(mapOf<BodyPart, Boolean>()) }

    Column(
        modifier = Modifier.padding(bottom = 72.dp).fillMaxSize().background(Background)
            .padding(horizontal = 16.dp)
    ) {
        HeaderSection()

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            RoundedCornerButton(
                width = 332,
                isEnabled = true,
                text = stringResource(Res.string.next),
                onClick = {
                    viewModel.updateStep(NewCustomerSteps.FinalInfo)
                })
        }
        UpperBodyMeasurementScreen(
            state = state,
            isSelected = selectedBodyParts.value[BodyPart.UPPER] ?: false,
            onBodyPartSelected = {
                selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                    this[BodyPart.UPPER] = !(this[BodyPart.UPPER] ?: false)
                }
            }
        ) {
            viewModel.upperSizeChanged(it)
        }
        LowerBodyMeasurementScreen(
            state = state,
            isSelected = selectedBodyParts.value[BodyPart.LOWER] ?: false,
            onBodyPartSelected = {
                selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                    this[BodyPart.LOWER] = !(this[BodyPart.LOWER] ?: false)
                }
            }
        ) {
            viewModel.lowerSizeChanged(it)
        }
        SleeveSizes(
            state = state,
            isSelected = selectedBodyParts.value[BodyPart.SLEEVES] ?: false,
            onBodyPartSelected = {
                selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                    this[BodyPart.SLEEVES] = !(this[BodyPart.SLEEVES] ?: false)
                }
            }
        ) {
            viewModel.sleevesSizeChanged(it)
        }

    }
}