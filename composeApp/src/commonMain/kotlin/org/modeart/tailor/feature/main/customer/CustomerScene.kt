package org.modeart.tailor.feature.main.customer

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.alphabetic
import modearttailor.composeapp.generated.resources.newest
import modearttailor.composeapp.generated.resources.oldest
import modearttailor.composeapp.generated.resources.others
import modearttailor.composeapp.generated.resources.personal
import modearttailor.composeapp.generated.resources.work_and_customer
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.customer.contract.CustomerUiState
import org.modeart.tailor.feature.main.home.HomeTopBar
import org.modeart.tailor.feature.main.home.HomeViewModel
import org.modeart.tailor.feature.main.home.contract.HomeUiEffect
import org.modeart.tailor.feature.main.note.NewNote
import org.modeart.tailor.feature.main.note.NoteItem
import org.modeart.tailor.feature.main.note.convertToNoteCategory
import org.modeart.tailor.model.business.NoteCategory
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.appTypography

@Composable
fun CustomerScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(CustomersViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<CustomerUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is CustomerUiEffect.Navigation -> onNavigate(effect.screen)
                is CustomerUiEffect.ShowRawNotification -> {
                    notification = effect
                }
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }

    LaunchedEffect(state.searchQuery){
        viewModel.searchForCustomers()
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        FilterTabs(viewModel::customerFilterChanged)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.businessCustomers.size) {
                NoteItem(title = "", content = "")
            }
        }
    }
}

enum class CustomerFilter { ALPHABETIC, NEWEST, OLDEST }

@Composable
@Preview
fun FilterTabs(onSelectedTab: (CustomerFilter) -> Unit) {

    var selectedTabIndex by remember { mutableStateOf(CustomerFilter.ALPHABETIC) }
    val tabTexts = listOf(
        stringResource(Res.string.alphabetic),
        stringResource(Res.string.newest),
        stringResource(Res.string.oldest)
    )

    val tabWidth = 120.dp
    val tabHeight = 56.dp
    val animatedOffset by animateDpAsState(
        targetValue = when (selectedTabIndex) {
            CustomerFilter.ALPHABETIC -> 0.dp
            CustomerFilter.NEWEST -> tabWidth
            CustomerFilter.OLDEST -> (tabWidth * 2)
        },
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier.width(360.dp).height(tabHeight)
            .background(color = AccentLight, shape = RoundedCornerShape(12.dp))
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
                        .clickable(interactionSource = null, indication = null) {
                            selectedTabIndex = index.convertToCustomerFilters()
                            onSelectedTab(index.convertToCustomerFilters())
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = appTypography().body14,
                        color = if (selectedTabIndex == index.convertToCustomerFilters()) Color.Black else Color.Gray
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
            Text(
                text = tabTexts[selectedTabIndex.ordinal],
                style = appTypography().body14,
                color = Color.White
            )
        }

    }
}

@Composable
fun CustomerItem() {
    Box(
        modifier = Modifier.size(115.dp, 144.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxSize(fraction = 0.5f).padding(2.dp)
                    .background(color = AccentLight, shape = RoundedCornerShape(10.dp))
            ) {
                // Avatar
            }
            // Name
            Text(text = "", style = appTypography().body13)
            // Phone
            Text(text = "", style = appTypography().body13)
        }
    }
}

fun Int.convertToCustomerFilters(): CustomerFilter {
    return when (this) {
        0 -> CustomerFilter.ALPHABETIC
        1 -> CustomerFilter.NEWEST
        2 -> CustomerFilter.OLDEST
        else -> CustomerFilter.OLDEST
    }
}
