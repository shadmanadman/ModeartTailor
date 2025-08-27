package org.modeart.tailor.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.theme.appTypography

@Composable
fun MainToolbar(title: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(94.dp).background(
            color = Color.Black,
            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        )
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.ic_back),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = appTypography().title15.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
@Preview
fun ToolbarPreview() {
    MainToolbar("Test")
}