package org.modeart.tailor.feature.onboarding.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.shape_welcome_1
import modearttailor.composeapp.generated.resources.shape_welcome_2
import modearttailor.composeapp.generated.resources.shape_welcome_3
import modearttailor.composeapp.generated.resources.shape_welcome_4
import modearttailor.composeapp.generated.resources.shape_welcome_5
import modearttailor.composeapp.generated.resources.shape_welcome_6
import modearttailor.composeapp.generated.resources.vector_first_welcome_page
import modearttailor.composeapp.generated.resources.vector_second_welcome_page
import modearttailor.composeapp.generated.resources.vector_third_welcome_page
import modearttailor.composeapp.generated.resources.welcome_page_1
import modearttailor.composeapp.generated.resources.welcome_page_2
import modearttailor.composeapp.generated.resources.welcome_page_3
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.feature.onboarding.welcome.contract.WelcomeScreenUiEffect
import org.modeart.tailor.navigation.OnBoardingNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Accent
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.OnBackground
import org.modeart.tailor.theme.appTypography

const val WELCOME_PAGE_COUNT = 3

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScene(
    onNavigate:(Route)-> Unit
) {
    val viewModel = koinViewModel(WelcomeViewModel::class)
    val effects = viewModel.effects.receiveAsFlow()

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                WelcomeScreenUiEffect.Navigation.Login -> onNavigate(OnBoardingNavigation.login)
                WelcomeScreenUiEffect.Navigation.SignUp -> onNavigate(OnBoardingNavigation.signup)
                is WelcomeScreenUiEffect.Navigation.Main -> onNavigate(effect.screen)
                is WelcomeScreenUiEffect.ShowRawNotification -> {}
            }
        }.collect()
    }

    val pagerState = rememberPagerState(pageCount = { WELCOME_PAGE_COUNT })
    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            WelcomePage(page)
        }
        WelcomeShapes(pagerState.currentPage, modifier = Modifier.align(Alignment.TopCenter))
        WelcomeIndicators(currentPage = pagerState.currentPage, modifier = Modifier.align(Alignment.BottomStart))
        DoneButton(
            pagerState.currentPage,
            modifier = Modifier.align(Alignment.BottomEnd),
            onDone = viewModel::onDone)
    }

}


@Composable
@Preview
fun WelcomePagePreview() {
    val pagerState = rememberPagerState(pageCount = { WELCOME_PAGE_COUNT })
    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            WelcomePage(page)
        }
        WelcomeShapes(pagerState.currentPage, modifier = Modifier.align(Alignment.TopCenter))
        WelcomeIndicators(currentPage = pagerState.currentPage, modifier = Modifier.align(Alignment.BottomStart))
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

    val shapes = when (page) {
        0 -> painterResource(Res.drawable.vector_first_welcome_page)
        1 -> painterResource(Res.drawable.vector_second_welcome_page)
        2 -> painterResource(Res.drawable.vector_third_welcome_page)
        else -> painterResource(Res.drawable.vector_first_welcome_page)
    }

    Box(
        modifier = modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = shapes, contentDescription = null)
            Text(
                modifier = Modifier.width(220.dp),
                text = pageText,
                textAlign = TextAlign.Center,
                style = appTypography().title16.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

        }
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
fun WelcomeIndicators(pageSize :Int = WELCOME_PAGE_COUNT,currentPage: Int, modifier: Modifier = Modifier) {

    LazyRow(modifier = modifier.padding(start = 15.dp, bottom = 30.dp)) {
        items(pageSize) { index ->
            val isSelected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 25.dp else 10.dp,
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
                label = "indicatorWidth"
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) OnBackground else Accent,
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
                    .background(color)
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


