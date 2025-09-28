package org.modeart.tailor.feature.main.note.contract

import org.jetbrains.compose.resources.StringResource
import org.modeart.tailor.navigation.Route

sealed interface NoteUiEffect {
    data class ShowRawNotification(val msg: String,val isError: Boolean = true, val errorCode: String = "") :
        NoteUiEffect

    data class ShowLocalizedNotification(val msg: StringResource, val errorCode: String = "") :
        NoteUiEffect


    data class Navigation(val screen: Route) : NoteUiEffect
}