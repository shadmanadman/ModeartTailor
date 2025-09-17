package org.modeart.tailor.feature.main.note.contract

import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.NoteCategory

data class NoteUiState(
    val currentCategory: NoteCategory = NoteCategory.WORK,
    val allNotes: List<BusinessProfile.Notes> = emptyList(),
    val newNoteTitle: String = "",
    val newNoteContent: String= "",
    val newNoteCategory: NoteCategory = NoteCategory.WORK,
    val isLoading: Boolean = false
)