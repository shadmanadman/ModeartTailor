package org.modeart.tailor.feature.onboarding.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.shape_welcome_1
import modearttailor.composeapp.generated.resources.shape_welcome_2
import modearttailor.composeapp.generated.resources.shape_welcome_3
import modearttailor.composeapp.generated.resources.shape_welcome_4
import modearttailor.composeapp.generated.resources.shape_welcome_5
import modearttailor.composeapp.generated.resources.shape_welcome_6
import modearttailor.composeapp.generated.resources.welcome_page_1
import modearttailor.composeapp.generated.resources.welcome_page_2
import modearttailor.composeapp.generated.resources.welcome_page_3
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.theme.appTypography

const val WELCOME_PAGE_COUNT = 3

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScene(
    goToLogin: () -> Unit,
    goToSignup: () -> Unit,
    goToMain: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { WELCOME_PAGE_COUNT })
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            WelcomePage(page)
        }
        WelcomeShapes(pagerState.currentPage, modifier = Modifier.align(Alignment.TopCenter))
        WelcomeIndicators(pagerState.currentPage, modifier = Modifier.align(Alignment.BottomStart))
        DoneButton(
            pagerState.currentPage,
            modifier = Modifier.align(Alignment.BottomEnd),
            onDone = {})
    }

}


@Composable
fun WelcomePage(page: Int, modifier: Modifier = Modifier) {
    val pageText = when (page) {
        0 -> stringResource(Res.string.welcome_page_1)
        1 -> stringResource(Res.string.welcome_page_2)
        2 -> stringResource(Res.string.welcome_page_3)
        else -> ""
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        // Content
        Text(
            modifier = Modifier.size(190.dp).align(Alignment.Center),
            text = pageText,
            style = appTypography().title16.copy(color = Color.Black, fontWeight = FontWeight.Bold)
        )

    }
}

@Composable
fun WelcomeShapes(currentPage: Int, modifier: Modifier = Modifier) {
    val shapes = listOf(
        Res.drawable.shape_welcome_1,
        Res.drawable.shape_welcome_2,
        Res.drawable.shape_welcome_3,
        Res.drawable.shape_welcome_4,
        Res.drawable.shape_welcome_5,
        Res.drawable.shape_welcome_6
    )

    val visibleShapes = remember(currentPage) {
        shapes.drop(currentPage * 2).take(2)
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        visibleShapes.forEachIndexed { index, shapeRes ->
            val isVisible = remember { mutableStateOf(false) }

            LaunchedEffect(currentPage) {
                isVisible.value = true
            }

            AnimatedVisibility(
                visible = isVisible.value,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = index * 200
                    )
                ) +
                        slideInHorizontally(
                            initialOffsetX = { if (index == 0) -it else it },
                            animationSpec = tween(durationMillis = 800, delayMillis = index * 300)
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                        slideOutHorizontally(
                            targetOffsetX = { if (index == 0) -it else it },
                            animationSpec = tween(durationMillis = 800)
                        )
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "shape_transition")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = "shape_scale"
                )
                Image(
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale),
                    painter = painterResource(shapeRes),
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
fun WelcomeIndicators(currentPage: Int, modifier: Modifier = Modifier) {

    LazyRow(modifier = modifier.padding(start = 15.dp, bottom = 30.dp)) {
        items(WELCOME_PAGE_COUNT) { index ->
            val isSelected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 25.dp else 10.dp,
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
                label = "indicatorWidth"
            )
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(height = 10.dp, width = width)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(Color.Black)
            )
        }

    }

}

@Composable
fun DoneButton(currentPage: Int, modifier: Modifier = Modifier, onDone: () -> Unit) {
    AnimatedVisibility(
        visible = currentPage == WELCOME_PAGE_COUNT - 1,
        modifier = modifier.padding(end = 20.dp, bottom = 20.dp),
        enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 300)) +
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 800, delayMillis = 300)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(durationMillis = 800)
                )
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Black).clickable(onClick = onDone),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Filled.Done,
                contentDescription = "Done Icon",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


