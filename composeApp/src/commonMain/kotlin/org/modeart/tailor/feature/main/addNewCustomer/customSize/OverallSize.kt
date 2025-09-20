package org.modeart.tailor.feature.main.addNewCustomer.customSize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.Background

enum class BodyPart {
    UPPER,
    LOWER,
    SLEEVES,
}

@Preview
@Composable
fun OverallSize() {
    val selectedBodyParts = remember { mutableStateOf(mapOf<BodyPart, Boolean>()) }

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        HeaderSection()
        UpperBodyMeasurementScreen(
            isSelected = selectedBodyParts.value[BodyPart.UPPER] ?: false,
        ) {
            selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                this[BodyPart.UPPER] = !(this[BodyPart.UPPER] ?: false)
            }
        }
        LowerBodyMeasurementScreen(isSelected = selectedBodyParts.value[BodyPart.LOWER] ?: false) {
            selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                this[BodyPart.LOWER] = !(this[BodyPart.LOWER] ?: false)
            }
        }
        SleeveSizes(isSelected = selectedBodyParts.value[BodyPart.SLEEVES] ?: false) {
            selectedBodyParts.value = selectedBodyParts.value.toMutableMap().apply {
                this[BodyPart.SLEEVES] = !(this[BodyPart.SLEEVES] ?: false)
            }
        }
    }
}