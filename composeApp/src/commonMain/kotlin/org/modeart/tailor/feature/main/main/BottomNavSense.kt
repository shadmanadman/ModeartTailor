package org.modeart.tailor.feature.main.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.modeart.tailor.feature.main.main.contract.BottomNavScreensState
import org.modeart.tailor.feature.main.main.contract.RootBottomNavId
import org.modeart.tailor.navigation.Route

@Composable
fun BottomNavScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(BottomNavViewModel::class)
    viewModel.effect.receiveAsFlow()

    Scaffold(bottomBar = {}, content = { innerPadding -> })
}

