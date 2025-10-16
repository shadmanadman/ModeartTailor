package org.modeart.tailor.feature.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.active_plan
import modearttailor.composeapp.generated.resources.current_plan
import modearttailor.composeapp.generated.resources.expired_plan
import modearttailor.composeapp.generated.resources.monthly_plan_title
import modearttailor.composeapp.generated.resources.pending_plan
import modearttailor.composeapp.generated.resources.plan_upgrade
import modearttailor.composeapp.generated.resources.subtitle_digital_notebook
import modearttailor.composeapp.generated.resources.yearly_plan_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.MainToolbar
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.PlanStatus
import org.modeart.tailor.model.business.PlanType
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun PlanHistoryScene() {

}

@Composable
@Preview
fun PlanHistoryContent() {
    Column(modifier = Modifier.background(Background)) {
        MainToolbar(stringResource(Res.string.plan_upgrade)) {

        }

        Box(Modifier.fillMaxWidth().background(AccentLight).padding(12.dp)) {
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
                CurrentPlanCard(isMonthly = true)
            }
        }

        LazyColumn {

        }
    }
}


@Composable
@Preview
fun CurrentPlanCard(isMonthly: Boolean) {
    val planType =
        if (isMonthly) stringResource(Res.string.monthly_plan_title) else stringResource(Res.string.yearly_plan_title)
    val daysLeft = if (isMonthly) "۲۸ روز باقیمانده" else "۳۶۵ روز باقیمانده"
    val activationDate = "فعال شده در ۲۵ مرداد ۱۴۰۴"
    val icon = if (isMonthly) Icons.Default.Schedule else Icons.Default.CalendarToday
    val backgroundColor = if (isMonthly) Primary else Accent
    val textColor = if (isMonthly) Color.White else Primary
    val iconTint = if (isMonthly) AccentLight else Primary


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
                text = item.dateOfPurchase,
                color = Primary.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Divider(color = Primary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}