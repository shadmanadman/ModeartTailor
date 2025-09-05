package org.modeart.tailor.feature.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.business_city
import modearttailor.composeapp.generated.resources.ic_add_photo
import modearttailor.composeapp.generated.resources.label_business
import modearttailor.composeapp.generated.resources.mobile_number
import modearttailor.composeapp.generated.resources.name_family_name
import modearttailor.composeapp.generated.resources.save
import modearttailor.composeapp.generated.resources.test_avatar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary

@Composable
fun EditeProfileScene() {
}


@Composable
fun EditeProfileContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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

        }


        OutlinedTextFieldModeArt(
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.name_family_name)
        )

        OutlinedTextFieldModeArt(
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.mobile_number)
        )

        OutlinedTextFieldModeArt(
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.label_business)
        )

        OutlinedTextFieldModeArt(
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.business_city)
        )

        RoundedCornerButton(
            isEnabled = true,
            text = stringResource(Res.string.save),
            onClick = { /*TODO*/ },
            backgroundColor = Primary
        )
    }
}


@Composable
@Preview
fun EditeProfilePreview() {
    EditeProfileContent()
}