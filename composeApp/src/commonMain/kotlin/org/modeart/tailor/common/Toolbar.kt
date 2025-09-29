package org.modeart.tailor.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_arrow_narrow_right
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Hint
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun MainToolbar(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(110.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(start = 18.dp, end = 18.dp, top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(40.dp).background(color = Accent, shape = CircleShape)
                    .padding(8.dp).clickable(onClick = onBack),
                painter = painterResource(Res.drawable.ic_arrow_narrow_right),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = appTypography().headline20.copy(
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        HorizontalDivider(Modifier.fillMaxWidth().align(Alignment.BottomCenter), color = Hint)
    }
}

@Composable
@Preview
fun ToolbarPreview() {
    MainToolbar("Test"){}
}