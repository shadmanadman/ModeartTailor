package org.modeart.tailor.feature.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.about_modart
import modearttailor.composeapp.generated.resources.contact_modart
import modearttailor.composeapp.generated.resources.edit_profile
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.ic_credit_card
import modearttailor.composeapp.generated.resources.ic_logout
import modearttailor.composeapp.generated.resources.ic_pencil
import modearttailor.composeapp.generated.resources.label_business
import modearttailor.composeapp.generated.resources.label_customers
import modearttailor.composeapp.generated.resources.label_validity
import modearttailor.composeapp.generated.resources.logout
import modearttailor.composeapp.generated.resources.test_avatar
import modearttailor.composeapp.generated.resources.update_plan
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.feature.main.profile.contract.ProfileUiState
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun MainProfileScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(ProfileViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<ProfileUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is ProfileUiEffect.Navigation -> onNavigate(effect.screen)
                is ProfileUiEffect.ShowRawNotification -> {
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

    MainProfileContent(state,viewModel)
}


@Composable
fun MainProfileContent(state: ProfileUiState, viewmodel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    ) {
        // Top section with profile image and details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Accent)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                // Placeholder for profile image
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEAEAEA))
                        .border(1.dp, Color(0xFFCCCCCC), CircleShape)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.test_avatar), // Replace with your image resource
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_add_photo),
                    contentDescription = "Edit Profile Picture",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.fullName,
                style = appTypography().title18,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                InfoCard(
                    label = stringResource(Res.string.label_validity),
                    value = "345"
                )
                InfoCard(
                    label = stringResource(Res.string.label_customers),
                    value = state.customerCount.toString()
                )
                InfoCard(
                    label = stringResource(Res.string.label_business),
                    value = state.businessName
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Menu items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp, vertical = 8.dp)
        ) {
            MenuItem(
                text = stringResource(Res.string.edit_profile),
                icon = vectorResource(Res.drawable.ic_pencil)
            ){
                viewmodel.navigateToEditProfile()
            }
            MenuItem(
                text = stringResource(Res.string.update_plan),
                icon = vectorResource(Res.drawable.ic_credit_card)
            ){
                viewmodel.navigateToEditProfile()
            }
            MenuItem(
                text = stringResource(Res.string.about_modart),
                icon = Icons.Default.Info
            ){
                viewmodel.navigateToAbout()
            }
            MenuItem(
                text = stringResource(Res.string.contact_modart),
                icon = Icons.Default.Message
            ){
                viewmodel.navigateToContact()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(Modifier.fillMaxWidth())
        // Logout button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp, vertical = 8.dp).clickable(onClick = viewmodel::logout)
        ) {
            MenuItem(
                text = stringResource(Res.string.logout),
                icon = vectorResource(Res.drawable.ic_logout),
                hideArrow = true
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFF0EAE1), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = appTypography().title16, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = appTypography().body14,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun MenuItem(text: String, icon: ImageVector, hideArrow: Boolean = false,onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (hideArrow.not())
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = appTypography().title16,
            color = Color.Black,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.background(
                color = AccentLight,
                shape = RoundedCornerShape(12.dp)
            ).padding(8.dp)
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    //MainProfileContent()
}