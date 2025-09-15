package org.modeart.tailor.feature.main.plans

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_calender_date_yearly
import modearttailor.composeapp.generated.resources.pay
import modearttailor.composeapp.generated.resources.vector_plans
import modearttailor.composeapp.generated.resources.yearly_plan_access
import modearttailor.composeapp.generated.resources.yearly_plan_customers
import modearttailor.composeapp.generated.resources.yearly_plan_notes
import modearttailor.composeapp.generated.resources.yearly_plan_price
import modearttailor.composeapp.generated.resources.yearly_plan_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun PlansDetailsScene() {
}


@Composable
fun PlansDetailsContent() {
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

        SubscriptionCardDetails(
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

        RoundedCornerButton(
            isEnabled = true,
            text = stringResource(Res.string.pay),
            onClick = { /*TODO*/ },
            backgroundColor = Primary
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
    buttonBackgroundColor: Color
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

        Button(
            onClick = { /* Handle subscription click */ },
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .offset(x = (-23).dp, y = (-20).dp)
                .align(Alignment.TopEnd),
            colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
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

@Composable
@Preview
fun PlansDetailsPreview() {
    PlansDetailsContent()
}