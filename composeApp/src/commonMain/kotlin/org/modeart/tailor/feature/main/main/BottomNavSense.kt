package org.modeart.tailor.feature.main.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.customer
import modearttailor.composeapp.generated.resources.home
import modearttailor.composeapp.generated.resources.ic_customer
import modearttailor.composeapp.generated.resources.ic_home
import modearttailor.composeapp.generated.resources.ic_note_add
import modearttailor.composeapp.generated.resources.measure
import modearttailor.composeapp.generated.resources.note
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.feature.main.home.HomeScene
import org.modeart.tailor.feature.main.main.contract.BottomNavScreensState
import org.modeart.tailor.feature.main.main.contract.BottomNavUiEffect
import org.modeart.tailor.feature.main.main.contract.RootBottomNavId
import org.modeart.tailor.feature.main.note.NoteScene
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.appTypography

@Composable
fun BottomNavScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(BottomNavViewModel::class)
    val state by viewModel.state.collectAsState()
    val effect = viewModel.effect.receiveAsFlow()

    val navigator = rememberNavigator()

    val bottomNavItems = bottomNavItems(
        currentSelected = state.selectedScreen,
        openScreen = viewModel::openScreen
    )

    LaunchedEffect(effect) {
        effect.onEach { effect ->
            when (effect) {
                BottomNavUiEffect.Navigation.Customers -> navigator.navigate(MainNavigation.customers.fullPath)
                BottomNavUiEffect.Navigation.Home -> navigator.navigate(MainNavigation.home.fullPath)
                BottomNavUiEffect.Navigation.Measure -> navigator.navigate(MainNavigation.measure.fullPath)
                BottomNavUiEffect.Navigation.Note -> navigator.navigate(MainNavigation.note.fullPath)
                is BottomNavUiEffect.ShowRawNotification -> {}
            }
        }.collect()
    }


    Scaffold(bottomBar = {
        BottomNavigationBar(items = bottomNavItems)
    }, content = { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navigator = navigator,
            navTransition = NavTransition(),
            initialRoute = MainNavigation.home.name
        ) {
            scene(route = MainNavigation.home.fullPath){
                HomeScene(onNavigate = { navigator.navigate(it.fullPath) })
            }
            scene(route = MainNavigation.measure.fullPath){
                HomeScene(onNavigate = { navigator.navigate(it.fullPath) })
            }
            scene(route = MainNavigation.note.fullPath){
                NoteScene(onNavigate = { navigator.navigate(it.fullPath) })
            }
        }
    })
}

@Composable
fun BottomNavigationBar(items: List<BottomNavScreensState>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            BottomNavigationItem(item = item)
        }
    }
}

@Composable
fun BottomNavigationItem(item: BottomNavScreensState) {
    val backgroundColor by animateColorAsState(
        targetValue = if (item.isSelected) Color.Black else Color.Transparent,
        animationSpec = tween(500)
    )
    val iconTint by animateColorAsState(
        targetValue = if (item.isSelected) Color.White else Color.Gray,
        animationSpec = tween(500)
    )
    val cornerRadius by animateDpAsState(
        targetValue = if (item.isSelected) 16.dp else 0.dp,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .clickable(onClick = item.openScreen)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(item.icon),
                contentDescription = item.name.toString(),
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Text(text = stringResource(item.name), style = appTypography().body12, color = iconTint)
        }
    }
}

@Composable
fun bottomNavItems(
    currentSelected: RootBottomNavId,
    openScreen: (RootBottomNavId) -> Unit
): List<BottomNavScreensState> {
    return listOf(
        BottomNavScreensState(
            id = RootBottomNavId.Home,
            name = Res.string.home,
            icon = Res.drawable.ic_home,
            isSelected = currentSelected == RootBottomNavId.Home,
            openScreen = { openScreen(RootBottomNavId.Home) }),
        BottomNavScreensState(
            id = RootBottomNavId.Note,
            name = Res.string.note,
            icon = Res.drawable.ic_note_add,
            isSelected = currentSelected == RootBottomNavId.Note,
            openScreen = { openScreen(RootBottomNavId.Note) }),
        BottomNavScreensState(
            id = RootBottomNavId.Customer,
            name = Res.string.customer,
            icon = Res.drawable.ic_customer,
            isSelected = currentSelected == RootBottomNavId.Customer,
            openScreen = { openScreen(RootBottomNavId.Customer) }),
        BottomNavScreensState(
            id = RootBottomNavId.Measure,
            name = Res.string.measure,
            icon = Res.drawable.ic_note_add,
            isSelected = currentSelected == RootBottomNavId.Measure,
            openScreen = { openScreen(RootBottomNavId.Measure) })

    )
}

