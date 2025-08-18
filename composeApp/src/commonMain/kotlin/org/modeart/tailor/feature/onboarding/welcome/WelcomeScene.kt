package org.modeart.tailor.feature.onboarding.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.welcome_page_1
import modearttailor.composeapp.generated.resources.welcome_page_2
import modearttailor.composeapp.generated.resources.welcome_page_3
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScene(
    goToLogin:()-> Unit,
    goToSignup:()-> Unit,
    goToMain:()-> Unit
){
    val pagerState = rememberPagerState(pageCount = {3})
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->

    }
}


@Composable
fun WelcomePage(page: Int, modifier: Modifier = Modifier){
    val pageText = when(page){
        1-> stringResource(Res.string.welcome_page_1)
        2-> stringResource(Res.string.welcome_page_2)
        3-> stringResource(Res.string.welcome_page_3)
        else -> ""
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Page: $page - Welcome to our awesome app!")
    }
}