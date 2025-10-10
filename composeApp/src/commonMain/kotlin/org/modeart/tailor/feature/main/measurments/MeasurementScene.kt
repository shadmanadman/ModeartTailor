package org.modeart.tailor.feature.main.measurments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.complete_measurement_title
import modearttailor.composeapp.generated.resources.ic_customer
import modearttailor.composeapp.generated.resources.ic_fast_measure
import modearttailor.composeapp.generated.resources.ic_full_measure
import modearttailor.composeapp.generated.resources.ic_user_cirlce_add
import modearttailor.composeapp.generated.resources.new_customer
import modearttailor.composeapp.generated.resources.old_customer
import modearttailor.composeapp.generated.resources.quick_measurement_description
import modearttailor.composeapp.generated.resources.quick_measurement_title
import modearttailor.composeapp.generated.resources.select_customer
import modearttailor.composeapp.generated.resources.select_customer_sub
import modearttailor.composeapp.generated.resources.subtitle_quick_or_complete
import modearttailor.composeapp.generated.resources.title_select_measurement_type
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementSelectedCustomer
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementStage
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementType
import org.modeart.tailor.feature.main.measurments.contracts.MeasurementUiEffect
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun MeasurementScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(MeasurementViewModel::class)
    val state by viewModel.state.collectAsState()
    val effects = viewModel.effect.receiveAsFlow()

    if (state.customerType == MeasurementSelectedCustomer.OldCustomer)
        SelectCustomerBottomSheet()

    var notification by remember { mutableStateOf<MeasurementUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is MeasurementUiEffect.Navigation -> onNavigate(effect.screen)
                is MeasurementUiEffect.ShowRawNotification -> {
                    notification = effect
                }

                is MeasurementUiEffect.ShowLocalizedNotification -> {}
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg) {
            notification = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.currentStage == MeasurementStage.SelectCustomer)
            CustomerTypeSelection(viewModel)
        else
            MeasurementTypeSelection(viewModel)
    }
}

@Composable
@Preview
fun CustomerTypeSelection(viewModel: MeasurementViewModel) {
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
            ) {
                viewModel.customerTypeSelected(customerType = MeasurementSelectedCustomer.NewCustomer)
                viewModel.measurementStageChanged(MeasurementStage.SelectMeasurementType)
            }
            CustomerSelectionCard(
                label = stringResource(Res.string.old_customer),
                backgroundColor = AccentLight,
                iconColor = AccentLight,
                labelColor = Primary,
                iconResId = Res.drawable.ic_customer,
                iconBackgroundColor = Primary
            ) {
                viewModel.customerTypeSelected(customerType = MeasurementSelectedCustomer.OldCustomer)
                viewModel.measurementStageChanged(MeasurementStage.SelectMeasurementType)
            }
        }
    }
}

@Composable
@Preview
fun MeasurementTypeSelection(viewModel: MeasurementViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(Res.string.title_select_measurement_type),
            style = appTypography().title16
        )
        Text(stringResource(Res.string.subtitle_quick_or_complete), style = appTypography().body14)
        MeasurementSelectionCard(
            label = stringResource(Res.string.quick_measurement_title),
            subtitle = stringResource(Res.string.quick_measurement_description),
            backgroundColor = Primary,
            labelColor = Accent,
            iconResId = Res.drawable.ic_fast_measure,
            onClick = {
                viewModel.measurementTypeSelected(MeasurementType.FastSize)
            })

        MeasurementSelectionCard(
            label = stringResource(Res.string.complete_measurement_title),
            subtitle = stringResource(Res.string.quick_measurement_description),
            backgroundColor = AccentLight,
            labelColor = Primary,
            iconResId = Res.drawable.ic_full_measure,
            onClick = {
                viewModel.measurementTypeSelected(MeasurementType.CustomSize)
            })
    }
}


@Composable
fun MeasurementSelectionCard(
    label: String,
    subtitle: String,
    backgroundColor: Color,
    labelColor: Color,
    iconResId: DrawableResource,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = label,
                modifier = Modifier
                    .size(90.dp)
                    .padding(bottom = 16.dp)
                    .padding(12.dp)
            )

            Column {
                Text(
                    text = label,
                    style = appTypography().title16,
                    fontWeight = FontWeight.SemiBold,
                    color = labelColor,
                )
                Text(
                    text = subtitle,
                    style = appTypography().body14,
                    color = labelColor,
                )
            }
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
            .clip(RoundedCornerShape(20.dp))
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

        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = labelColor,
            textAlign = TextAlign.Center
        )
    }
}