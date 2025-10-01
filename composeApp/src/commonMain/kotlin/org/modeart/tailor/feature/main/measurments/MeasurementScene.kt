package org.modeart.tailor.feature.main.measurments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_add_button
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_customer
import modearttailor.composeapp.generated.resources.ic_user_cirlce_add
import modearttailor.composeapp.generated.resources.new_customer
import modearttailor.composeapp.generated.resources.old_customer
import modearttailor.composeapp.generated.resources.select_customer
import modearttailor.composeapp.generated.resources.select_customer_sub
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun MeasurementScene(onNavigate: (Route) -> Unit) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        MeasurementContent()
    }
}

@Composable
@Preview
fun MeasurementContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(Res.string.select_customer), style = appTypography().title16)
        Text(stringResource(Res.string.select_customer_sub), style = appTypography().body14)

        Row(modifier = Modifier.padding(top = 16.dp)) {
            CustomerSelectionCard(
                label = stringResource(Res.string.new_customer),
                backgroundColor = Primary,
                iconColor = Primary,
                labelColor = AccentLight,
                iconResId = Res.drawable.ic_user_cirlce_add,
                iconBackgroundColor = AccentLight
            ) {}
            CustomerSelectionCard(
                label = stringResource(Res.string.old_customer),
                backgroundColor = AccentLight,
                iconColor = AccentLight,
                labelColor = Primary,
                iconResId = Res.drawable.ic_customer,
                iconBackgroundColor = Primary
            ) {}
        }
    }
}


@Composable
fun CustomerSelectionCard(
    label: String,
    backgroundColor: Color,
    iconColor: Color,
    labelColor: Color,
    iconBackgroundColor: Color,
    iconResId: DrawableResource,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(142.dp, 150.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp)) // Rounded corners
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp), // Inner padding
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier
                .size(70.dp)
                .padding(bottom = 16.dp)
                .background(color = iconBackgroundColor, shape = RoundedCornerShape(18.dp))
                .padding(12.dp)
        )

        // Label Text
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = labelColor,
            textAlign = TextAlign.Center
        )
    }
}