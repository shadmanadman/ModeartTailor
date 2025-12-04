package org.modeart.tailor.feature.main.plans

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_calender_date_yearly
import modearttailor.composeapp.generated.resources.monthly_plan_access
import modearttailor.composeapp.generated.resources.monthly_plan_customers
import modearttailor.composeapp.generated.resources.monthly_plan_notes
import modearttailor.composeapp.generated.resources.monthly_plan_price
import modearttailor.composeapp.generated.resources.monthly_plan_title
import modearttailor.composeapp.generated.resources.pay
import modearttailor.composeapp.generated.resources.vector_plans
import modearttailor.composeapp.generated.resources.yearly_plan_access
import modearttailor.composeapp.generated.resources.yearly_plan_customers
import modearttailor.composeapp.generated.resources.yearly_plan_notes
import modearttailor.composeapp.generated.resources.yearly_plan_price
import modearttailor.composeapp.generated.resources.yearly_plan_title
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.plans.contract.PlanUiEffect
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.model.business.PlanType
import org.modeart.tailor.platform.openWebBrowser
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun PlansDetailsScene() {
    val viewModel = koinViewModel(PlansViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<PlanUiEffect.ShowRawNotification?>(null) }
    var paymentUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is PlanUiEffect.Navigation -> Unit
                is PlanUiEffect.ShowRawNotification -> {
                    notification = effect
                }

                is PlanUiEffect.ShowLocalizedNotification -> {}
                is PlanUiEffect.SendToPayment -> {paymentUrl = effect.url}
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }
    LaunchedEffect(paymentUrl){
        paymentUrl?.let {
            openWebBrowser(it)
        }
    }
    PlansDetailsContent(state.selectedPlan, viewModel)
}


@Composable
fun PlansDetailsContent(planType: PlanType,plansViewModel: PlansViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(Res.drawable.vector_plans),
            contentDescription = null
        )

        if (planType == PlanType.YEARLY)
            SubscriptionCardDetails(
                title = stringResource(Res.string.yearly_plan_title),
                access = stringResource(Res.string.yearly_plan_access),
                customers = stringResource(Res.string.yearly_plan_customers),
                notes = stringResource(Res.string.yearly_plan_notes),
                price = stringResource(Res.string.yearly_plan_price),
                backgroundColor = Accent,
                contentColor = Primary,
                icon = vectorResource(Res.drawable.ic_calender_date_yearly),
                buttonBackgroundColor = Primary,
                buttonTextColor = Color.White
            )
        else
            SubscriptionCardDetails(
                title = stringResource(Res.string.monthly_plan_title),
                access = stringResource(Res.string.monthly_plan_access),
                customers = stringResource(Res.string.monthly_plan_customers),
                notes = stringResource(Res.string.monthly_plan_notes),
                price = stringResource(Res.string.monthly_plan_price),
                backgroundColor = Primary,
                contentColor = Accent,
                icon = vectorResource(Res.drawable.ic_calender_date_yearly),
                buttonBackgroundColor = Accent,
                buttonTextColor = Primary
            )



        RoundedCornerButton(
            isEnabled = true,
            text = stringResource(Res.string.pay),
            onClick = { plansViewModel.generatePaymentUrl() },
            backgroundColor = Primary,
            textColor = Color.White
        )
    }
}

@Composable
fun SubscriptionCardDetails(
    title: String,
    access: String,
    customers: String,
    notes: String,
    price: String,
    backgroundColor: Color,
    contentColor: Color,
    icon: ImageVector,
    buttonBackgroundColor: Color,
    buttonTextColor: Color
) {
    Box(modifier = Modifier.size(290.dp, 275.dp).padding(top = 24.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = appTypography().title18,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        modifier = Modifier.background(
                            buttonBackgroundColor,
                            RoundedCornerShape(18.dp)
                        )
                            .clip(RoundedCornerShape(22.dp)).padding(12.dp),
                        imageVector = icon,
                        contentDescription = null,
                        tint = backgroundColor,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SubscriptionDetails(
                    text = access,
                    textColor = contentColor,
                    bulletPointColor = buttonBackgroundColor
                )
                SubscriptionDetails(
                    text = customers,
                    textColor = contentColor,
                    bulletPointColor = buttonBackgroundColor
                )
                SubscriptionDetails(
                    text = notes,
                    textColor = contentColor,
                    bulletPointColor = buttonBackgroundColor
                )

                Spacer(modifier = Modifier.height(24.dp))

            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .offset(x = (13).dp, y = (-20).dp)
                .align(Alignment.TopStart)
                .clip(RoundedCornerShape(18.dp))
                .background(buttonBackgroundColor),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = price,
                color = if (backgroundColor == Accent) Accent else Primary,
                style = appTypography().title17,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SubscriptionDetails(text: String, textColor: Color, bulletPointColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(bulletPointColor, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = appTypography().title16, color = textColor)
    }
}
//
//@Composable
//@Preview
//fun PlansDetailsPreview() {
//    PlansDetailsContent(PlanType.YEARLY)
//}