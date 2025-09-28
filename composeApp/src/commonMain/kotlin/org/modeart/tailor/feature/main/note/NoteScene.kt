package org.modeart.tailor.feature.main.note

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.first_note
import modearttailor.composeapp.generated.resources.ic_add_button
import modearttailor.composeapp.generated.resources.new_note
import modearttailor.composeapp.generated.resources.others
import modearttailor.composeapp.generated.resources.personal
import modearttailor.composeapp.generated.resources.work_and_customer
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.feature.main.home.HomeTopBar
import org.modeart.tailor.feature.main.note.contract.NoteUiEffect
import org.modeart.tailor.feature.main.profile.ProfileViewModel
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.model.business.NoteCategory
import org.modeart.tailor.navigation.MainNavigation
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Primary
import org.modeart.tailor.theme.appTypography

@Composable
fun NoteScene(onNavigate: (Route) -> Unit) {
    val viewModel = koinViewModel(NoteViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<NoteUiEffect.ShowRawNotification?>(null) }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is NoteUiEffect.Navigation -> onNavigate(effect.screen)
                is NoteUiEffect.ShowRawNotification -> {
                    notification = effect
                }

                is NoteUiEffect.ShowLocalizedNotification -> {}
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }

    Column {
        HomeTopBar(
            onNavigateToProfile = viewModel::navigateToProfile,
            onSearchQueryCompleted = viewModel::navigateToSearch,
            onNavigateToNotification = viewModel::navigateToNavigation
        )
        NoteTabs(onSelectedTab = viewModel::noteCategorySelected)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                NewNote(onNewNote = { onNavigate(MainNavigation.newNote) })
            }
            items(state.allNotes.size) {
                NoteItem(title = state.allNotes[it].title, content = state.allNotes[it].content)
            }
        }
    }
}

@Composable
@Preview
fun NoteTabs(onSelectedTab: (NoteCategory) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(NoteCategory.WORK) }
    val tabTexts = listOf(
        stringResource(Res.string.work_and_customer),
        stringResource(Res.string.personal),
        stringResource(Res.string.others)
    )

    val tabWidth = 120.dp
    val tabHeight = 56.dp
    val animatedOffset by animateDpAsState(
        targetValue = when (selectedTabIndex) {
            NoteCategory.WORK -> 0.dp
            NoteCategory.PERSONAL -> tabWidth
            NoteCategory.OTHERS -> (tabWidth * 2)
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
                        .clickable {
                            selectedTabIndex = index.convertToNoteCategory()
                            onSelectedTab(index.convertToNoteCategory())
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = appTypography().body14,
                        color = if (selectedTabIndex == index.convertToNoteCategory()) Color.Black else Color.Gray
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
@Preview
fun EmptyNote() {
    Box(
        modifier = Modifier.width(241.dp).defaultMinSize(minHeight = 296.dp)
            .background(color = AccentLight, shape = RoundedCornerShape(16.dp))
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
fun NewNote(onNewNote: () -> Unit) {
    Box(
        modifier = Modifier.size(172.dp)
            .background(color = AccentLight, shape = RoundedCornerShape(16.dp))
            .padding(16.dp).clickable(onClick = onNewNote), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(Res.drawable.ic_add_button), contentDescription = null)
            Text(text = stringResource(Res.string.new_note), style = appTypography().title15)
        }

    }
}


@Composable
@Preview
fun NoteItem(title: String, content: String) {
    Box(modifier = Modifier.width(172.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .heightIn(max = 200.dp)
        ) {
            Text(
                text = "title",
                style = appTypography().body14.copy(color = Primary, fontWeight = FontWeight.Bold)
            )
            Text(text = "content", style = appTypography().body14)
        }
    }
}


fun Int.convertToNoteCategory(): NoteCategory {
    return when (this) {
        0 -> NoteCategory.WORK
        1 -> NoteCategory.PERSONAL
        2 -> NoteCategory.OTHERS
        else -> NoteCategory.WORK
    }
}