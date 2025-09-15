package org.modeart.tailor.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.modeart.tailor.theme.Hint
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
    roundedCornerShape: Dp = 24.dp,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.size(width.dp, height.dp)
            .background(backgroundColor, shape = RoundedCornerShape(roundedCornerShape)),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Hint,
            containerColor = backgroundColor
        ),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Text(text = text, color = textColor, style = appTypography().title16)
    }
}