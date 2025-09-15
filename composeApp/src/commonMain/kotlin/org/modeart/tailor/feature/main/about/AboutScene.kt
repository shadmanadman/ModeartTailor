package org.modeart.tailor.feature.main.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.about_modart
import modearttailor.composeapp.generated.resources.about_us_content
import modearttailor.composeapp.generated.resources.app_subtitle
import modearttailor.composeapp.generated.resources.each_body_has_its_own_story
import modearttailor.composeapp.generated.resources.vector_plans
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.OnSecondary
import org.modeart.tailor.theme.appTypography
import org.modeart.tailor.theme.getArabicShinFont

@Composable
fun AboutScene() {
}


@Composable
fun AboutContent() {
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

        Column(
            modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                .border(width = 1.dp, color = Hint, shape = RoundedCornerShape(12.dp))
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(Res.string.about_modart), style = appTypography().headline20)
            Text(stringResource(Res.string.about_us_content), style = appTypography().body13)
        }
        Text(
            stringResource(Res.string.each_body_has_its_own_story),
            style = appTypography().title18.copy(fontFamily = getArabicShinFont())
        )
        Text(
            stringResource(Res.string.app_subtitle),
            style = appTypography().body14.copy(OnSecondary)
        )

    }
}


@Composable
@Preview
fun AboutPreview() {
    AboutContent()
}