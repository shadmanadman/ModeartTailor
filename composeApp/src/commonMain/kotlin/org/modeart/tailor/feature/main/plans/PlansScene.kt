package org.modeart.tailor.feature.main.plans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_calendar_date_monthly
import modearttailor.composeapp.generated.resources.ic_calender_date_yearly
import modearttailor.composeapp.generated.resources.monthly_plan_access
import modearttailor.composeapp.generated.resources.monthly_plan_customers
import modearttailor.composeapp.generated.resources.monthly_plan_notes
import modearttailor.composeapp.generated.resources.monthly_plan_price
import modearttailor.composeapp.generated.resources.monthly_plan_title
import modearttailor.composeapp.generated.resources.select_subscription
import modearttailor.composeapp.generated.resources.subtitle_digital_notebook
import modearttailor.composeapp.generated.resources.yearly_plan_access
import modearttailor.composeapp.generated.resources.yearly_plan_customers
import modearttailor.composeapp.generated.resources.yearly_plan_notes
import modearttailor.composeapp.generated.resources.yearly_plan_price
import modearttailor.composeapp.generated.resources.yearly_plan_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun PlansScene() {

}

@Composable
fun SubscriptionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F4ED)) // Light background color from the image
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.select_subscription),
            style = appTypography().title18,
            fontWeight = FontWeight.Bold,
            color = Primary
        )
        Text(
            text = stringResource(Res.string.subtitle_digital_notebook),
            style = appTypography().title16,
            color = Hint,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Monthly Subscription Card
        SubscriptionCard(
            title = stringResource(Res.string.monthly_plan_title),
            access = stringResource(Res.string.monthly_plan_access),
            customers = stringResource(Res.string.monthly_plan_customers),
            notes = stringResource(Res.string.monthly_plan_notes),
            price = stringResource(Res.string.monthly_plan_price),
            backgroundColor = Primary,
            contentColor = Background,
            icon = vectorResource(Res.drawable.ic_calendar_date_monthly),
            buttonBackgroundColor = Accent
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Yearly Subscription Card
        SubscriptionCard(
            title = stringResource(Res.string.yearly_plan_title),
            access = stringResource(Res.string.yearly_plan_access),
            customers = stringResource(Res.string.yearly_plan_customers),
            notes = stringResource(Res.string.yearly_plan_notes),
            price = stringResource(Res.string.yearly_plan_price),
            backgroundColor = Accent,
            contentColor = Primary,
            icon = vectorResource(Res.drawable.ic_calender_date_yearly),
            buttonBackgroundColor = Primary
        )
    }
}

@Composable
fun SubscriptionCard(
    title: String,
    access: String,
    customers: String,
    notes: String,
    price: String,
    backgroundColor: Color,
    contentColor: Color,
    icon: ImageVector,
    buttonBackgroundColor: Color
) {
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
                modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.background(buttonBackgroundColor, RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(22.dp)).padding(18.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = backgroundColor,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SubscriptionDetail(
                text = access,
                textColor = contentColor,
                bulletPointColor = buttonBackgroundColor
            )
            SubscriptionDetail(
                text = customers,
                textColor = contentColor,
                bulletPointColor = buttonBackgroundColor
            )
            SubscriptionDetail(
                text = notes,
                textColor = contentColor,
                bulletPointColor = buttonBackgroundColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle subscription click */ },
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = price,
                    color = if (backgroundColor == Accent) Background else Primary,
                    style = appTypography().title17,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SubscriptionDetail(text: String, textColor: Color, bulletPointColor: Color) {
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

@Preview
@Composable
fun PreviewSubscriptionScreen() {
    SubscriptionScreen()
}