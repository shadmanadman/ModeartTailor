package org.modeart.tailor.feature.main.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.active_plan
import modearttailor.composeapp.generated.resources.buy_new_plan
import modearttailor.composeapp.generated.resources.buy_new_plan_to_access
import modearttailor.composeapp.generated.resources.current_plan
import modearttailor.composeapp.generated.resources.expired_plan
import modearttailor.composeapp.generated.resources.ic_danger
import modearttailor.composeapp.generated.resources.ic_logout
import modearttailor.composeapp.generated.resources.monthly_plan_title
import modearttailor.composeapp.generated.resources.no_purchase_has_been_made
import modearttailor.composeapp.generated.resources.pending_plan
import modearttailor.composeapp.generated.resources.plan_upgrade
import modearttailor.composeapp.generated.resources.save_size
import modearttailor.composeapp.generated.resources.subtitle_digital_notebook
import modearttailor.composeapp.generated.resources.yearly_plan_title
import modearttailor.composeapp.generated.resources.your_plan_has_been_ended
import modearttailor.composeapp.generated.resources.your_plan_history
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.MainToolbar
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.addNewCustomer.contract.NewCustomerSteps
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.PlanStatus
import org.modeart.tailor.model.business.PlanType
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun PlanHistoryScene(onBack: () -> Unit,onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(ProfileViewModel::class)
    val state by viewModel.uiState.collectAsState()
    PlanHistoryContent(state.plans, state.remainingPlanInDays.toString(), onBack,onNavigate)
}

@Composable
fun PlanHistoryContent(
    plans: List<BusinessProfile.Plan>,
    remainingDays: String,
    onBack: () -> Unit,
    onNavigate: (Route) -> Unit
) {
    val currentPlan = plans.firstOrNull { it.planStatus == PlanStatus.ACTIVE }
    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column() {
            MainToolbar(stringResource(Res.string.plan_upgrade)) {
                onBack()
            }

            Box(
                Modifier.fillMaxWidth().background(AccentLight).padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.current_plan),
                        style = appTypography().title18
                    )
                    Text(
                        text = stringResource(Res.string.subtitle_digital_notebook),
                        style = appTypography().body14
                    )
                    if (currentPlan == null)
                        NoCurrentPlan()
                    else
                        CurrentPlanCard(currentPlan, remainingDays)

                }
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(Res.string.your_plan_history),
                style = appTypography().title17
            )

            if (plans.isEmpty())
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(Res.string.no_purchase_has_been_made),
                    style = appTypography().title17
                )
            else
                LazyColumn {
                    items(plans.size) {
                        HistoryItem(plans[it])
                    }
                }

        }
        RoundedCornerButton(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp),
            isEnabled = true,
            text = stringResource(Res.string.buy_new_plan)
        ) {
            onNavigate(MainNavigation.plan)
        }
    }
}

@Composable
fun NoCurrentPlan() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2C2420)
            ),
            modifier = Modifier
                .width(260.dp)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFDFCBA6),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_danger),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = stringResource(Res.string.your_plan_has_been_ended),
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = stringResource(Res.string.buy_new_plan_to_access),
                    color = Color(0xFFD0C6B6),
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
fun CurrentPlanCard(plan: BusinessProfile.Plan, remainingDays: String) {
    val planType =
        if (plan.planType == PlanType.MONTHLY) stringResource(Res.string.monthly_plan_title) else stringResource(
            Res.string.yearly_plan_title
        )
    val daysLeft = remainingDays
    val activationDate = convertMillisToDate(plan.dateOfPurchase)
    val icon =
        if (plan.planType == PlanType.MONTHLY) Icons.Default.Schedule else Icons.Default.CalendarToday
    val backgroundColor = if (plan.planType == PlanType.MONTHLY) Primary else Accent
    val textColor = if (plan.planType == PlanType.MONTHLY) Color.White else Primary
    val iconTint = if (plan.planType == PlanType.MONTHLY) AccentLight else Primary


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = planType,
                    color = textColor,
                    style = appTypography().title18,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activationDate,
                    color = textColor.copy(alpha = 0.8f),
                    textAlign = TextAlign.Right,
                    style = appTypography().body14,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = daysLeft,
                    color = textColor.copy(alpha = 0.8f),
                    style = appTypography().body14,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = icon,
                contentDescription = "Plan Icon",
                tint = iconTint,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}


@Composable
fun StatusTag(status: PlanStatus) {
    val (text, color, textColor) = when (status) {
        PlanStatus.ACTIVE -> Triple(
            stringResource(Res.string.active_plan),
            Color.Green.copy(alpha = 0.2f),
            Color.Green
        )

        PlanStatus.EXPIRED -> Triple(
            stringResource(Res.string.expired_plan),
            Color.Red.copy(alpha = 0.2f),
            Color.Red
        )

        PlanStatus.PENDING -> Triple(
            stringResource(Res.string.pending_plan),
            Accent.copy(alpha = 0.2f),
            Accent
        )
    }

    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun HistoryItem(item: BusinessProfile.Plan) {
    val planType = if (item.planType == PlanType.YEARLY)
        stringResource(Res.string.yearly_plan_title)
    else
        stringResource(Res.string.monthly_plan_title)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusTag(status = item.planStatus)

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = planType,
                style = appTypography().title16,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = convertMillisToDate(item.dateOfPurchase),
                color = Primary.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Divider(color = Primary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

@OptIn(ExperimentalTime::class)
fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val dateTimeInLocalZone = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTimeInLocalZone.month.name} ${dateTimeInLocalZone.day}, ${dateTimeInLocalZone.year}"
}