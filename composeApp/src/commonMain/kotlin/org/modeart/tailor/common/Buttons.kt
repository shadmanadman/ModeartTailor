package org.modeart.tailor.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.login
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun RoundedCornerButton(
    isEnabled: Boolean,
    text: String,
    textColor: Color = Color.White,
    width: Int = 265,
    height: Int = 54,
    backgroundColor: Color = Primary,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.size(width.dp, height.dp).clip(
            RoundedCornerShape(24.dp)
        ).background(backgroundColor),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Text(text = text, color = textColor, style = appTypography().title16)
    }
}