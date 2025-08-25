package org.modeart.tailor.feature.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.all_customer
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_notification
import modearttailor.composeapp.generated.resources.ic_target
import modearttailor.composeapp.generated.resources.ic_user_star
import modearttailor.composeapp.generated.resources.search_in_customers
import modearttailor.composeapp.generated.resources.this_month_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.appTypography

@Composable
fun HomeScene(onNavigate: (Route) -> Unit) {

}


@Composable
@Preview
fun HomeTopBar() {
    Box(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier.fillMaxWidth().background(
                color = Color.Black,
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
            ).padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {


                // Profile Picture
                Image(
                    painter = painterResource(Res.drawable.ic_add_photo),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape)
                )
                // User Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "مریم جان خوش آمدی",
                        color = Color.White,
                        style = appTypography().title15
                    )
                    Text(
                        text = "۰۹۱۷۱۷۴۹۱۱۷",
                        color = Color.White,
                        style = appTypography().title15,
                        fontSize = 14.sp
                    )
                }

                // Notification Icon
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(48.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_notification),
                        contentDescription = "Notifications"
                    )
                    // Dot indicator for unread notifications
                    Box(
                        modifier = Modifier.padding(bottom = 12.dp, start = 12.dp).size(7.dp)
                            .background(color = Color.Red, shape = CircleShape)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.padding(top = 100.dp, start = 30.dp, end = 30.dp)
                .align(Alignment.BottomCenter).fillMaxWidth().height(40.dp)
                .background(color = Color.White, RoundedCornerShape(16.dp)).padding(6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Notifications"
                )
                BasicTextField(
                    value = "",
                    onValueChange = { /* Handle text change */ },
                    textStyle = appTypography().body12
                ) {
                    Text(
                        text = stringResource(Res.string.search_in_customers),
                        style = appTypography().body12
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun Statistics() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        // Customers count
        Box(
            modifier = Modifier.size(154.dp, 137.dp)
                .background(Color.Black, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier.size(50.dp, 45.dp).background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_user_star),
                        contentDescription = "Notifications"
                    )

                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(Res.string.all_customer),
                    style = appTypography().title15.copy(color = Color.White)
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "12",
                    style = appTypography().title15.copy(color = Color.White)
                )
            }
        }

        // This month customer
        Box(
            modifier = Modifier.size(154.dp, 137.dp)
                .background(Color(0xffebedf3), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier.size(50.dp, 45.dp).background(
                        Color.Black,
                        RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_target),
                        contentDescription = "Notifications", tint = Color.White
                    )

                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(Res.string.this_month_customer),
                    style = appTypography().title15
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "12",
                    style = appTypography().title15
                )
            }
        }
    }
}