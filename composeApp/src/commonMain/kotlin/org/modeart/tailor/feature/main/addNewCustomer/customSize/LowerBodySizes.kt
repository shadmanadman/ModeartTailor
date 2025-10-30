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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ankle_circumference
import modearttailor.composeapp.generated.resources.calf_circumference
import modearttailor.composeapp.generated.resources.hip_circumference
import modearttailor.composeapp.generated.resources.ic_lower_body_icon
import modearttailor.composeapp.generated.resources.inseam
import modearttailor.composeapp.generated.resources.knee_circumference
import modearttailor.composeapp.generated.resources.lower_body
import modearttailor.composeapp.generated.resources.outseam
import modearttailor.composeapp.generated.resources.thigh_circumference
import modearttailor.composeapp.generated.resources.waist_and_skirt_or_pants
import modearttailor.composeapp.generated.resources.waist_to_hip_length
import modearttailor.composeapp.generated.resources.waist_to_knee_length
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerUiState
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography


@Composable
fun LowerBodyMeasurementScreen(
    state: NewCustomerUiState,
    isSelected: Boolean = false,
    onBodyPartSelected: (BodyPart) -> Unit = {},
    onLowerBodySizeChanged: (CustomerProfile.LowerBodySizes) -> Unit

) {
    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 800.dp else 60.dp,
        animationSpec = tween(durationMillis = 500)
    )
    var lowerBodySize by remember { mutableStateOf(CustomerProfile.LowerBodySizes()) }

    Column(modifier = Modifier.offset(x = 10.dp, y = -40.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        val fields = listOf(
            stringResource(Res.string.waist_and_skirt_or_pants),
            stringResource(Res.string.hip_circumference),
            stringResource(Res.string.waist_to_hip_length),
            stringResource(Res.string.thigh_circumference),
            stringResource(Res.string.knee_circumference),
            stringResource(Res.string.calf_circumference),
            stringResource(Res.string.ankle_circumference),
            stringResource(Res.string.inseam),
            stringResource(Res.string.outseam),
            stringResource(Res.string.waist_to_knee_length)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onBodyPartSelected(BodyPart.LOWER) })
        ) {
            Box(
                Modifier.size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_lower_body_icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(Res.string.lower_body),
                style = appTypography().headline20,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val values = state.customer.lowerBodySizes
        Row {

            VerticalDivider(
                modifier = Modifier.height(animatedHeight).padding(start = 28.dp)
                    .offset(y = (-20).dp),
                color = AccentLight,
                thickness = 4.dp
            )

            AnimatedVisibility(isSelected) {
                val focusManager = LocalFocusManager.current
                val focusRequesters = remember {
                    List(fields.size) { FocusRequester() }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(start = 38.dp, end = 12.dp)
                ) {
                    itemsIndexed(fields) { index,label ->
                        OutlinedTextFieldModeArt(
                            oneLine = true,
                            textStyle = appTypography().title15,
                            modifier = Modifier.padding(top = 12.dp)
                                .focusRequester(focusRequesters[index]),
                            value = when(label){
                                fields[0] -> values?.waistbandCircumference?:""
                                fields[1] -> values?.hipCircumference?:""
                                fields[2] -> values?.waistToHipLength?:""
                                fields[3] -> values?.thighCircumference?:""
                                fields[4] -> values?.kneeCircumference?:""
                                fields[5] -> values?.calfCircumference?:""
                                fields[6] -> values?.ankleCircumference?:""
                                fields[7] -> values?.inseamLength?:""
                                fields[8] -> values?.outseamLength?:""
                                fields[9] -> values?.waistToKneeLength?:""
                                else -> ""
                            },
                            onValueChange = {
                                lowerBodySize = when (label) {
                                    fields[0] -> lowerBodySize.copy(waistbandCircumference = it)
                                    fields[1] -> lowerBodySize.copy(hipCircumference = it)
                                    fields[2] -> lowerBodySize.copy(waistToHipLength = it)
                                    fields[3] -> lowerBodySize.copy(thighCircumference = it)
                                    fields[4] -> lowerBodySize.copy(kneeCircumference = it)
                                    fields[5] -> lowerBodySize.copy(calfCircumference = it)
                                    fields[6] -> lowerBodySize.copy(ankleCircumference = it)
                                    fields[7] -> lowerBodySize.copy(inseamLength = it)
                                    fields[8] -> lowerBodySize.copy(outseamLength = it)
                                    fields[9] -> lowerBodySize.copy(waistToKneeLength = it)
                                    else -> lowerBodySize
                                }
                                onLowerBodySizeChanged(lowerBodySize)
                            },
                            isNumberOnly = true,
                            hint = label,
                            onDone = {
                                if (index < fields.size - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    focusManager.clearFocus()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LowerMeasurementScreenPreview() {
    //LowerBodyMeasurementScreen()
}
