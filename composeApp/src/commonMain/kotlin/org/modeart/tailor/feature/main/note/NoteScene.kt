package org.modeart.tailor.feature.main.note

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.first_note
import modearttailor.composeapp.generated.resources.ic_add_button
import modearttailor.composeapp.generated.resources.new_note
import modearttailor.composeapp.generated.resources.others
import modearttailor.composeapp.generated.resources.personal
import modearttailor.composeapp.generated.resources.work_and_customer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.appTypography

@Composable
fun NoteScene(onNavigate: (Route) -> Unit) {

}

@Composable
@Preview
fun NoteTabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTexts = listOf(
        stringResource(Res.string.work_and_customer),
        stringResource(Res.string.personal),
        stringResource(Res.string.others)
    )

    val tabWidth = 120.dp
    val tabHeight = 56.dp
    val animatedOffset by animateDpAsState(
        targetValue = when (selectedTabIndex) {
            0 -> 0.dp
            1 -> tabWidth
            2 -> (tabWidth * 2)
            else -> 0.dp
        },
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier.width(360.dp).height(tabHeight)
            .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabTexts.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTabIndex = index },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = appTypography().body14,
                        color = if (selectedTabIndex == index) Color.Black else Color.Gray
                    )
                }
            }
        }

        // Animated rounded box
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(tabWidth)
                .height(tabHeight)
                .background(color = Color.Black, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedTabIndex in tabTexts.indices) {
                Text(
                    text = tabTexts[selectedTabIndex],
                    style = appTypography().body14,
                    color = Color.White
                )
            }
        }

    }
}


@Composable
@Preview
fun EmptyNote() {
    Box(
        modifier = Modifier.width(241.dp).defaultMinSize(minHeight = 296.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .alpha(0.3f)
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(Res.drawable.ic_add_button), contentDescription = null)
            Text(text = stringResource(Res.string.first_note), style = appTypography().title15)
        }

    }
}

@Composable
@Preview
fun NewNote() {
    Box(
        modifier = Modifier.size(172.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .alpha(0.3f)
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(Res.drawable.ic_add_button), contentDescription = null)
            Text(text = stringResource(Res.string.new_note), style = appTypography().title15)
        }

    }
}
