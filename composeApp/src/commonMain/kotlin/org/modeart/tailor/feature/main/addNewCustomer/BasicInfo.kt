package org.modeart.tailor.feature.main.addNewCustomer

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.birth_date
import modearttailor.composeapp.generated.resources.choose_gender
import modearttailor.composeapp.generated.resources.customer_basic_info
import modearttailor.composeapp.generated.resources.customer_name_family_name
import modearttailor.composeapp.generated.resources.customer_picture
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_man
import modearttailor.composeapp.generated.resources.ic_upload
import modearttailor.composeapp.generated.resources.ic_woman
import modearttailor.composeapp.generated.resources.mobile_number_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography


@Composable
@Preview
fun BasicInfo() {
    Column {
        Text(text = stringResource(Res.string.customer_basic_info), style = appTypography().title15)

        // Select Gender
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 150.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var selectedTabIndex by remember { mutableStateOf(0) }
            val tabWidth = 70.dp
            val tabHeight = 64.dp
            val animatedOffset by animateDpAsState(
                targetValue = when (selectedTabIndex) {
                    0 -> 0.dp
                    1 -> tabWidth
                    else -> 0.dp
                },
                animationSpec = tween(durationMillis = 500)
            )
            Text(text = stringResource(Res.string.choose_gender), style = appTypography().body13)
            Box(
                modifier = Modifier.width(140.dp).height(tabHeight)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(tabWidth)
                        .height(tabHeight)
                        .background(color = Color.Black, shape = RoundedCornerShape(12.dp))
                )
                Row(
                    modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(painter = painterResource(Res.drawable.ic_man), contentDescription = null)
                    Image(
                        painter = painterResource(Res.drawable.ic_woman),
                        contentDescription = null
                    )
                }
            }
        }

        // Name, mobile and birthday
        Column(modifier = Modifier.padding(top = 18.dp)) {
            TextField(
                value = "", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .height(64.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(color = Color.LightGray),
                label = {
                    Text(
                        stringResource(Res.string.customer_name_family_name),
                        style = appTypography().body13
                    )
                },
                textStyle = appTypography().body13,
            )

            TextField(
                value = "", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .height(64.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(color = Color.LightGray),
                label = {
                    Text(
                        stringResource(Res.string.mobile_number_customer),
                        style = appTypography().body13
                    )
                },
                textStyle = appTypography().body13,
            )

            TextField(
                value = "", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .height(64.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(color = Color.LightGray),
                label = {
                    Text(
                        stringResource(Res.string.birth_date),
                        style = appTypography().body13
                    )
                },
                textStyle = appTypography().body13,
            )
        }

        // Customer picture
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.customer_picture),
                style = appTypography().body13
            )

            // Upload
            Box(
                modifier = Modifier.padding(end = 16.dp).size(64.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(Res.drawable.ic_upload), contentDescription = null)
            }
            // Take picture
            Box(
                modifier = Modifier.size(64.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add_photo),
                    contentDescription = null
                )
            }
        }
    }
}