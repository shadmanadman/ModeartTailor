package org.modeart.tailor.feature.main.addNewCustomer.customSize

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.armhole
import modearttailor.composeapp.generated.resources.bicep_circumference
import modearttailor.composeapp.generated.resources.forearm_circumference
import modearttailor.composeapp.generated.resources.full_sleeve_length
import modearttailor.composeapp.generated.resources.ic_sleeve
import modearttailor.composeapp.generated.resources.sleeve
import modearttailor.composeapp.generated.resources.sleeve_length_to_elbow
import modearttailor.composeapp.generated.resources.wrist_circumference
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.appTypography

@Composable
fun SleeveSizes(
    state: NewCustomerUiState,
    isSelected: Boolean = false, onBodyPartSelected: (BodyPart) -> Unit = {},
    onSleevesSizeChanged: (CustomerProfile.SleevesSizes) -> Unit
) {
    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 400.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)
    )
    var sleevesSize by remember { mutableStateOf(CustomerProfile.SleevesSizes()) }
    Column(modifier = Modifier.offset(x = 10.dp, y = (-70).dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        val fields = listOf(
            stringResource(Res.string.full_sleeve_length),
            stringResource(Res.string.forearm_circumference),
            stringResource(Res.string.bicep_circumference),
            stringResource(Res.string.wrist_circumference),
            stringResource(Res.string.armhole),
            stringResource(Res.string.sleeve_length_to_elbow)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onBodyPartSelected(BodyPart.SLEEVES) })
        ) {
            Box(
                Modifier.size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_sleeve),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(Res.string.sleeve),
                style = appTypography().headline20,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val values = state.customer.sleevesSizes
        Row(modifier = Modifier.fillMaxWidth()) {

            VerticalDivider(
                modifier = Modifier.height(animatedHeight).padding(start = 28.dp)
                    .offset(y = (-20).dp),
                color = AccentLight,
                thickness = 4.dp
            )

            AnimatedVisibility(visible = isSelected) {
                LazyColumn(
                    modifier = Modifier.wrapContentHeight()
                        .padding(start = 38.dp, end = 12.dp)
                ) {
                    items(fields) { label ->
                        OutlinedTextFieldModeArt(
                            modifier = Modifier.padding(top = 12.dp),
                            value = when (label) {
                                fields[0] -> values?.fullSleeveLength?:""
                                fields[1] -> values?.forearmCircumference?:""
                                fields[2] -> values?.armCircumference?:""
                                fields[3] -> values?.wristCircumference?:""
                                fields[4] -> values?.sleeveHole?:""
                                fields[5] -> values?.sleeveLengthToElbow?:""
                                else -> ""
                            },
                            onValueChange = {
                                sleevesSize = getUpdatedSleevesSize(label, it, fields, sleevesSize)
                                onSleevesSizeChanged(sleevesSize)
                            },
                            isNumberOnly = true,
                            hint = label
                        )
                    }
                }
            }
        }
    }
}

private fun getUpdatedSleevesSize(
    label: String,
    value: String,
    fields: List<String>,
    currentSleevesSize: CustomerProfile.SleevesSizes
): CustomerProfile.SleevesSizes {
    return when (label) {
        fields[0] -> currentSleevesSize.copy(fullSleeveLength = value)
        fields[1] -> currentSleevesSize.copy(forearmCircumference = value)
        fields[2] -> currentSleevesSize.copy(armCircumference = value)
        fields[3] -> currentSleevesSize.copy(wristCircumference = value)
        fields[4] -> currentSleevesSize.copy(sleeveHole = value)
        fields[5] -> currentSleevesSize.copy(sleeveLengthToElbow = value)
        else -> currentSleevesSize
    }
}

@Composable
@Preview
fun SleeveSizesPreview() {
    //SleeveSizes()
}