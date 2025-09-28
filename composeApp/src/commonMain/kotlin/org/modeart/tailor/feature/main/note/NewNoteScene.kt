package org.modeart.tailor.feature.main.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
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
import org.modeart.tailor.common.OutlinedTextFieldModeArt
import org.modeart.tailor.common.RoundedCornerButton
import org.modeart.tailor.feature.main.note.contract.NoteUiEffect
import org.modeart.tailor.navigation.Route
import org.modeart.tailor.theme.Background
import org.modeart.tailor.theme.appTypography

@Composable
@Preview
fun NewNoteScene(onNavigate: (Route) -> Unit) {
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
            }
        }.collect()
    }
    notification?.let { notif ->
        InAppNotification(message = notif.msg, networkErrorCode = notif.errorCode) {
            notification = null
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Background).padding(32.dp)
    ) {
        NoteCategory()
        AddNewNote()
        RoundedCornerButton(text = stringResource(Res.string.save), isEnabled = state.isEnabled) {}
    }

}


@Composable
fun NoteCategory() {
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
        Checkbox(checked = false, onCheckedChange = {})
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(Res.string.personal), style = appTypography().body13)
        Checkbox(checked = true, onCheckedChange = {})
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(Res.string.others), style = appTypography().body13)
        Checkbox(checked = false, onCheckedChange = {})
    }
}

@Composable
@Preview
fun AddNewNote() {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            hint = stringResource(Res.string.title)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextFieldModeArt(
            modifier = Modifier.fillMaxWidth(),
            value = "", onValueChange = {},
            height = 440.dp,
            hint = stringResource(Res.string.note_content)
        )
    }
}

@Composable
@Preview
fun NotePreview() {

}
