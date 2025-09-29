package org.modeart.tailor.feature.main.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import modearttailor.composeapp.generated.resources.in_category
import modearttailor.composeapp.generated.resources.new_note
import modearttailor.composeapp.generated.resources.note_content
import modearttailor.composeapp.generated.resources.others
import modearttailor.composeapp.generated.resources.personal
import modearttailor.composeapp.generated.resources.save
import modearttailor.composeapp.generated.resources.title
import modearttailor.composeapp.generated.resources.work_and_customer
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.modeart.tailor.common.InAppNotification
import org.modeart.tailor.common.MainToolbar
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.note.contract.NoteUiEffect
import org.modeart.tailor.feature.main.note.contract.NoteUiState
import org.modeart.tailor.model.business.NoteCategory
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.AccentLight
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography

@Composable
@Preview
fun NewNoteScene(onNavigate: (Route) -> Unit, onBack: () -> Unit) {
    val viewModel = koinViewModel(NoteViewModel::class)
    val state by viewModel.uiState.collectAsState()
    val effects = viewModel.effects.receiveAsFlow()
    var notification by remember { mutableStateOf<NoteUiEffect.ShowRawNotification?>(null) }
    var notificationLocalized by remember {
        mutableStateOf<NoteUiEffect.ShowLocalizedNotification?>(
            null
        )
    }

    LaunchedEffect(effects) {
        effects.onEach { effect ->
            when (effect) {
                is NoteUiEffect.Navigation -> onNavigate(effect.screen)
                is NoteUiEffect.ShowRawNotification -> {
                    notification = effect
                }

                is NoteUiEffect.ShowLocalizedNotification -> {
                    notificationLocalized = effect
                }
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }
    notificationLocalized?.let { notif ->
        InAppNotification(message = stringResource(notif.msg)) {
            notificationLocalized = null
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Background)
    ) {
        MainToolbar(title = stringResource(Res.string.new_note)) {
            onBack()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(32.dp)
        ) {
            NoteCategory(viewModel::newNoteCategorySelected)
            Spacer(modifier = Modifier.height(16.dp))
            AddNewNote(state, viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            if (state.isLoading)
                CircularProgressIndicator()
            else
                RoundedCornerButton(
                    text = stringResource(Res.string.save),
                    isEnabled = state.isEnabled
                ) {
                    viewModel.newNote()
                }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCategory(onCategorySelected: (NoteCategory) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categoryOptions = mapOf(
        NoteCategory.WORK to Res.string.work_and_customer,
        NoteCategory.PERSONAL to  Res.string.personal,
        NoteCategory.OTHERS to Res.string.others
    )
    var selectedOption by remember { mutableStateOf(categoryOptions.entries.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // --- 1. The Main (Selected) Category Box ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // The main box has a light background and rounded corners
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(55.dp) // Fixed height to match the look
                .clickable(onClick = { expanded = true }) // Makes the whole row clickable
                .padding(horizontal = 16.dp), // Padding inside the row
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End // Since the text is right-aligned (RTL)
        ) {
            // Dropdown Indicator Icon (e.g., a simple down arrow)
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // The Box with the Highlighted Selection Text
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center) // Center content within this box
                    .background(
                        color = AccentLight,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                // The main selected text (e.g., "Work and Customer Category")
                Text(
                    text = stringResource(selectedOption.value), // Use the text of the selected item
                    style = appTypography().body14 // Use your defined style
                )
            }
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(80.dp, 47.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.in_category),
                style = appTypography().body14.copy(color = Color.Gray)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(Res.string.work_and_customer), style = appTypography().body13)
        Checkbox(checked = false, onCheckedChange = { onCategorySelected(NoteCategory.WORK) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(Res.string.personal), style = appTypography().body13)
        Checkbox(checked = true, onCheckedChange = { onCategorySelected(NoteCategory.PERSONAL) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(Res.string.others), style = appTypography().body13)
        Checkbox(checked = false, onCheckedChange = { onCategorySelected(NoteCategory.OTHERS) })
    }
}

@Composable
@Preview
fun AddNewNote(state: NoteUiState, viewModel: NoteViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth(),
            value = state.newNoteTitle,
            onValueChange = viewModel::newNoteTitleChanged,
            hint = stringResource(Res.string.title)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth(),
            value = state.newNoteContent, onValueChange = viewModel::newNoteContentChanged,
            height = 440.dp,
            hint = stringResource(Res.string.note_content)
        )
    }
}

@Composable
@Preview
fun NotePreview() {

}
