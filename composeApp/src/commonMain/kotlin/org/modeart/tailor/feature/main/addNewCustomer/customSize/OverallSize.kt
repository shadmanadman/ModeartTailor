package org.modeart.tailor.feature.main.addNewCustomer.customSize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.save_and_next
import modearttailor.composeapp.generated.resources.save_customer
import modearttailor.composeapp.generated.resources.save_size
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.NewCustomerViewModel
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerBodyForm
import org.modeart.tailor.model.customer.CustomerShoulder
import org.modeart.tailor.model.customer.CustomerStyle
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background

enum class BodyPart {
    UPPER,
    LOWER,
    SLEEVES,
}

@Preview
@Composable
fun OverallSize(isRegisterNewSize:Boolean,state: NewCustomerUiState, viewModel: NewCustomerViewModel) {
    val selectedBodyParts = remember { mutableStateOf(mapOf<BodyPart, Boolean>()) }

    Column(
        modifier = Modifier.padding(bottom = 72.dp).fillMaxSize().background(Background)
            .padding(horizontal = 16.dp)
    ) {
        HeaderSection(
            age = state.customer.age?:"0",
            name = state.customer.name?:"",
            phoneNumber = state.customer.phoneNumber?:"",
            avatar = state.customer.avatar?:""
        )

        Row(Modifier.fillMaxWidth()) {
            if (isRegisterNewSize)
                RoundedCornerButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Accent,
                    textColor = Color.Black,
                    isEnabled = true,
                    text = stringResource(Res.string.save_size),
                    onClick = {
                        viewModel.addSize()
                    })
            else {
                RoundedCornerButton(
                    modifier = Modifier.padding(end = 16.dp),
                    width = 232,
                    isEnabled = true,
                    text = stringResource(Res.string.save_and_next),
                    onClick = {
                        viewModel.updateStep(NewCustomerSteps.FinalInfo)
                    })

                RoundedCornerButton(
                    isEnabled = true,
                    backgroundColor = Accent,
                    textColor = Color.Black,
                    text = stringResource(Res.string.save_customer),
                    onClick = {
                        viewModel.updateStep(NewCustomerSteps.FinalInfo)
                        viewModel.addSize()
                    })
            }
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