package org.modeart.tailor.feature.main.addNewCustomer.size

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.age
import modearttailor.composeapp.generated.resources.back_armhole
import modearttailor.composeapp.generated.resources.back_neck_to_waist_length
import modearttailor.composeapp.generated.resources.body_shape
import modearttailor.composeapp.generated.resources.chest_circumference
import modearttailor.composeapp.generated.resources.chest_gap
import modearttailor.composeapp.generated.resources.chest_height
import modearttailor.composeapp.generated.resources.front_armhole
import modearttailor.composeapp.generated.resources.front_waist_length
import modearttailor.composeapp.generated.resources.hip_length
import modearttailor.composeapp.generated.resources.ic_nerrow_down_body_type
import modearttailor.composeapp.generated.resources.ic_upper_body_icon
import modearttailor.composeapp.generated.resources.large_hip_circumference
import modearttailor.composeapp.generated.resources.neck_circumference
import modearttailor.composeapp.generated.resources.shoulder_width
import modearttailor.composeapp.generated.resources.size
import modearttailor.composeapp.generated.resources.small_hip_circumference
import modearttailor.composeapp.generated.resources.small_shoulder
import modearttailor.composeapp.generated.resources.style
import modearttailor.composeapp.generated.resources.test_avatar
import modearttailor.composeapp.generated.resources.top_measurements
import modearttailor.composeapp.generated.resources.waist_circumference
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.appTypography


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "۳۸",
                color = Color.Black,
                style = appTypography().body14,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Accent, shape = RoundedCornerShape(8.dp))
                    .padding(6.dp)
            )
            Text(
                stringResource(Res.string.age),
                color = Color.White,
                style = appTypography().body14
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "مدرن", color = Color.Black, style = appTypography().body14,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .background(color = Accent, shape = RoundedCornerShape(8.dp))
                    .padding(6.dp)
            )
            Text(
                stringResource(Res.string.style),
                color = Color.White,
                style = appTypography().body14
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(Res.drawable.ic_nerrow_down_body_type),
                contentDescription = null,
                tint = Accent,
                modifier = Modifier.size(24.dp)
            )
            Text(
                stringResource(Res.string.body_shape),
                color = Color.White,
                style = appTypography().body14
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "۳۸",
                color = Color.Black,
                style = appTypography().body14,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Accent, shape = RoundedCornerShape(8.dp))
                    .padding(6.dp)
            )
            Text(
                stringResource(Res.string.size),
                color = Color.White,
                style = appTypography().body14
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("مریم احمدی", color = Color.White, style = appTypography().body13)
                Text("0912*******", color = Color.Gray, style = appTypography().body13)
            }
            Image(
                painter = painterResource(Res.drawable.test_avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun UpperBodyMeasurementScreen(
    isSelected: Boolean = false,
    onBodyPartSelected: (BodyPart) -> Unit
) {
    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 800.dp else 60.dp,
        animationSpec = tween(durationMillis = 500)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        val fields = listOf(
            stringResource(Res.string.shoulder_width),
            stringResource(Res.string.small_shoulder),
            stringResource(Res.string.neck_circumference),
            stringResource(Res.string.chest_height),
            stringResource(Res.string.chest_circumference),
            stringResource(Res.string.chest_gap),
            stringResource(Res.string.front_waist_length),
            stringResource(Res.string.waist_circumference),
            stringResource(Res.string.small_hip_circumference),
            stringResource(Res.string.large_hip_circumference),
            stringResource(Res.string.hip_length),
            stringResource(Res.string.front_armhole),
            stringResource(Res.string.back_armhole),
            stringResource(Res.string.back_neck_to_waist_length)
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = {
            onBodyPartSelected(BodyPart.UPPER)
        })) {
            Box(
                Modifier.size(64.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_upper_body_icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(Res.string.top_measurements),
                style = appTypography().headline20,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val values = remember { mutableStateMapOf<String, String>() }
        Row {

            VerticalDivider(
                modifier = Modifier.height(animatedHeight)
                    .padding(start = 28.dp).offset(y = (-20).dp),
                color = AccentLight,
                thickness = 4.dp
            )

            AnimatedVisibility(visible = isSelected) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(start = 38.dp, end = 12.dp)
                ) {
                    items(fields) { label ->
                        OutlinedTextFieldModeArt(
                            modifier = Modifier.padding(top = 12.dp),
                            value = values[label] ?: "",
                            onValueChange = { values[label] = it },
                            isNumberOnly = true,
                            hint = label
                        )
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun UpperMeasurementScreenPreview() {
    UpperBodyMeasurementScreen(isSelected = true) {}
}