package org.modeart.tailor.feature.main.customer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.extensions.persianMonth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.acquaintance_period
import modearttailor.composeapp.generated.resources.code
import modearttailor.composeapp.generated.resources.fast_size
import modearttailor.composeapp.generated.resources.login
import modearttailor.composeapp.generated.resources.lower_body
import modearttailor.composeapp.generated.resources.measure
import modearttailor.composeapp.generated.resources.sleeve
import modearttailor.composeapp.generated.resources.upper_body
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.api.BASE_URL
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.customer.contract.CustomerUiState
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile
import org.modeart.tailor.model.customer.SizeType
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun CustomerProfileScene(onNavigate: (Route) -> Unit,onBack:()-> Unit) {
    val viewModel = koinViewModel(CustomersViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<CustomerUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is CustomerUiEffect.Navigation -> onNavigate(effect.screen)
                is CustomerUiEffect.ShowRawNotification -> {
                    notification = effect
                }
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }


    CustomerProfileContent(state.selectedCustomer ?: CustomerProfile(),onBack)
}

@Composable
fun CustomerProfileContent(profile: CustomerProfile,onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        ProfileHeaderCard(profile,onBack)
        LazyColumn {
            items(profile.sizes?.size ?: 0) {
                MeasurementItem(
                    profile.sizes?.get(it)?.createdAt ?: "",
                    profile.sizes?.get(it)?.type ?: emptyList(),
                    profile.sizes?.get(it)?.createdAt ?: ""
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileHeaderCard(customerProfile: CustomerProfile = CustomerProfile(),onBack: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp,
                    topEnd = 0.dp,
                    topStart = 0.dp
                )
            ), colors = CardDefaults.cardColors(containerColor = Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .clickable(onClick = onBack),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = customerProfile.name.toString(),
                    color = Color.White,
                    style = appTypography().title18,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Go to profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Picture
            AsyncImage(
                model = "$BASE_URL${customerProfile.avatar}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(
                    CircleShape
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number
            Text(
                text = customerProfile.phoneNumber.toString(),
                color = Color.White,
                style = appTypography().body14,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileActionButton(
                    label = stringResource(Res.string.acquaintance_period),
                    value = "3 Years"
                )
                ProfileActionButton(label = stringResource(Res.string.measure), value = "437")
                ProfileActionButton(
                    label = stringResource(Res.string.code),
                    value = customerProfile.code.toString()
                )
            }
        }
    }
}


@Composable
fun ProfileActionButton(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(100.dp)
            .height(70.dp), colors = CardDefaults.cardColors(containerColor = AccentLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = Primary,
                style = appTypography().body13,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                color = Primary,
                style = appTypography().title16,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MeasurementItem(date: String, types: List<SizeType>, tag: String) {
    var type = ""
    types.forEach { type += "/${it.toLocal()}" }
    val dateInPersian = PersianDateTime.parse(date.toLong())
    Card(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { /* Handle item click */ },
        border = BorderStroke(1.dp, Accent),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Card(
//                modifier = Modifier.background(AccentLight),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = tag,
//                    color = Primary,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
//                )
//            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${dateInPersian.day} ${dateInPersian.persianMonth().displayName} ${dateInPersian.year}",
                    color = Primary,
                    style = appTypography().body14,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = type,
                    color = Color.Gray,
                    style = appTypography().body13
                )
            }
        }
    }
}

private fun SizeType.toLocal(): StringResource{
    return when(this){
        SizeType.UpperBody -> Res.string.upper_body
        SizeType.LowerBody -> Res.string.lower_body
        SizeType.Sleeves -> Res.string.sleeve
        SizeType.FastSize -> Res.string.fast_size
    }
}