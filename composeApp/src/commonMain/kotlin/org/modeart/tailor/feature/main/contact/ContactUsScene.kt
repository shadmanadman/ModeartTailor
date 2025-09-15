package org.modeart.tailor.feature.main.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.contact_ways
import modearttailor.composeapp.generated.resources.how_can_we_help
import modearttailor.composeapp.generated.resources.ic_telegram
import modearttailor.composeapp.generated.resources.ic_whatsapp
import modearttailor.composeapp.generated.resources.message_title
import modearttailor.composeapp.generated.resources.send
import modearttailor.composeapp.generated.resources.vector_plans
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun ContactUsScene() {
}


@Composable
fun ContactUs() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Accent)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(Res.drawable.vector_plans),
                contentDescription = null
            )
        }

        OutlinedTextFieldModeArt(
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.message_title)
        )
        OutlinedTextFieldModeArt(
            modifier = Modifier.height(230.dp),
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.how_can_we_help)
        )

        RoundedCornerButton(
            isEnabled = true,
            text = stringResource(Res.string.send),
            onClick = { /*TODO*/ },
            backgroundColor = Primary
        )


        Row(
            modifier = Modifier.fillMaxWidth(fraction = 0.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.contact_ways),
                style = appTypography().body14,
                modifier = Modifier.weight(1f)
            )
            Image(painter = painterResource(Res.drawable.ic_telegram), contentDescription = null)
            Image(painter = painterResource(Res.drawable.ic_whatsapp), contentDescription = null)

        }
    }
}


@Preview
@Composable
fun ContactUsPreview() {
    ContactUs()
}