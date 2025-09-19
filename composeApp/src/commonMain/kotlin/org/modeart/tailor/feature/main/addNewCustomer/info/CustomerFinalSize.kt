package org.modeart.tailor.feature.main.addNewCustomer.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.back_armhole
import modearttailor.composeapp.generated.resources.back_neck_to_waist_length
import modearttailor.composeapp.generated.resources.chest_circumference
import modearttailor.composeapp.generated.resources.chest_gap
import modearttailor.composeapp.generated.resources.chest_height
import modearttailor.composeapp.generated.resources.compare_with_prev_size
import modearttailor.composeapp.generated.resources.fit
import modearttailor.composeapp.generated.resources.front_armhole
import modearttailor.composeapp.generated.resources.front_waist_length
import modearttailor.composeapp.generated.resources.hip_length
import modearttailor.composeapp.generated.resources.ic_body_form_1
import modearttailor.composeapp.generated.resources.knee_circumference
import modearttailor.composeapp.generated.resources.large_hip_circumference
import modearttailor.composeapp.generated.resources.neck_circumference
import modearttailor.composeapp.generated.resources.prev_size
import modearttailor.composeapp.generated.resources.shoulder_width
import modearttailor.composeapp.generated.resources.size
import modearttailor.composeapp.generated.resources.small_hip_circumference
import modearttailor.composeapp.generated.resources.small_shoulder
import modearttailor.composeapp.generated.resources.thigh_circumference
import modearttailor.composeapp.generated.resources.view_profile
import modearttailor.composeapp.generated.resources.waist_circumference
import modearttailor.composeapp.generated.resources.waist_to_hip_length
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography

@Composable
@Preview
fun CustomerFinalSizeScenePreview() {
    CustomerFinalSizeScene()
}

@Composable
fun CustomerFinalSizeScene() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.prev_size, "۴ ماه پیش"),
                style = appTypography().body13
            )
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(Res.string.compare_with_prev_size),
                    style = appTypography().body13
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Image with overlay
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 350.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_body_form_1),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }


            // Measurement list
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MeasurementItem(label = stringResource(Res.string.shoulder_width), value = "۱۲")
                MeasurementItem(label = stringResource(Res.string.small_shoulder), value = "۸۸")
                MeasurementItem(label = stringResource(Res.string.neck_circumference), value = "۸۸")
                MeasurementItem(label = stringResource(Res.string.chest_height), value = "۶۷")
                MeasurementItem(
                    label = stringResource(Res.string.chest_circumference),
                    value = "۴۵"
                )
                MeasurementItem(label = stringResource(Res.string.chest_gap), value = "۷۷")
                MeasurementItem(label = stringResource(Res.string.front_waist_length), value = "۸۳")
                MeasurementItem(
                    label = stringResource(Res.string.waist_circumference),
                    value = "۸۸"
                )
                MeasurementItem(
                    label = stringResource(Res.string.small_hip_circumference),
                    value = "۹۹"
                )
                MeasurementItem(
                    label = stringResource(Res.string.large_hip_circumference),
                    value = "۵۸"
                )
                MeasurementItem(label = stringResource(Res.string.hip_length), value = "۸۳")
                MeasurementItem(label = stringResource(Res.string.front_armhole), value = "۸۸")
                MeasurementItem(label = stringResource(Res.string.back_armhole), value = "۴۴")
                MeasurementItem(
                    label = stringResource(Res.string.back_neck_to_waist_length),
                    value = "۴۴"
                )
                MeasurementItem(
                    label = stringResource(Res.string.waist_to_hip_length),
                    value = "۴۴"
                )

                MeasurementItem(
                    label = stringResource(Res.string.thigh_circumference),
                    value = "۴۴"
                )

                MeasurementItem(
                    label = stringResource(Res.string.knee_circumference),
                    value = "۴۴"
                )

            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Bottom info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OptionBox(stringResource(Res.string.fit))
            OptionBox(stringResource(Res.string.fit))
            OptionBox(stringResource(Res.string.size))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(Res.string.view_profile), style = appTypography().body13)
        }
    }
}

@Composable
private fun MeasurementItem(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label, style = appTypography().body13, maxLines = 1
        )
        Text(
            text = value, style = appTypography().body13
        )

    }
}


@Composable
fun OptionBox(text: String) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = appTypography().body13,
            color = Color.Black
        )
    }
}
