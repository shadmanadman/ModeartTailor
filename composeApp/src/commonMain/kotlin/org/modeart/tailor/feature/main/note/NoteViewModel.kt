package org.modeart.tailor.feature.main.note

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.note_registered
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.business.BusinessService
import org.modeart.tailor.feature.main.customer.contract.CustomerUiEffect
import org.modeart.tailor.feature.main.note.contract.NoteUiEffect
import org.modeart.tailor.feature.main.note.contract.NoteUiState
import org.modeart.tailor.feature.main.profile.contract.ProfileUiEffect
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.NoteCategory
import org.modeart.tailor.navigation.MainNavigation
import kotlin.toString

class NoteViewModel(private val businessService: BusinessService) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteUiState())

    val uiState: StateFlow<NoteUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    var effects = Channel<NoteUiEffect>(Channel.UNLIMITED)
        private set

    fun noteCategorySelected(category: NoteCategory) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }

    fun newNoteCategorySelected(category: NoteCategory) {
        _uiState.update { it.copy(newNoteCategory = category) }
    }

    fun newNoteTitleChanged(title: String) {
        _uiState.update { it.copy(newNoteTitle = title) }
    }

    fun newNoteContentChanged(content: String) {
        _uiState.update {
            it.copy(newNoteContent = content)
        }
    }

    fun navigateToProfile() {
        effects.trySend(NoteUiEffect.Navigation(MainNavigation.profile))
    }

    fun navigateToSearch() {
        effects.trySend(NoteUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun navigateToNavigation() {
        effects.trySend(NoteUiEffect.Navigation(MainNavigation.editProfile))
    }

    fun emptyNewNote(){
        _uiState.update { it.copy(newNoteContent = "", newNoteCategory = NoteCategory.WORK, newNoteTitle = "") }
    }

    fun getAllNotes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = businessService.getBusinessNotes()) {
                is ApiResult.Error -> {
                    effects.send(
                        NoteUiEffect.ShowRawNotification(
                            msg = response.message, errorCode = response.code.toString()
                        )
                    )
                    _uiState.update { it.copy(isLoading = false) }
                }

                is ApiResult.Success -> {
                    _uiState.update { it.copy(allNotes = response.data, isLoading = false) }
                }
            }
        }
    }

    fun newNote() {
        if (_uiState.value.newNoteTitle.isEmpty() || _uiState.value.newNoteContent.isEmpty())
            return

        viewModelScope.launch {
            val response = _uiState.value.run {
                businessService.createNote(
                    BusinessProfile.Notes(
                        title = newNoteTitle,
                        content = newNoteContent,
                        category = newNoteCategory
                    )
                )
            }
            _uiState.update { it.copy(isLoading = true) }

            when (response) {
                is ApiResult.Error -> effects.send(
                    NoteUiEffect.ShowRawNotification(
                        msg = response.message, errorCode = response.code.toString()
                    )
                )

                is ApiResult.Success -> {
                    effects.send(
                        NoteUiEffect.ShowLocalizedNotification(
                            msg = Res.string.note_registered
                        )
                    )
                    effects.send(NoteUiEffect.OnBack)
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}