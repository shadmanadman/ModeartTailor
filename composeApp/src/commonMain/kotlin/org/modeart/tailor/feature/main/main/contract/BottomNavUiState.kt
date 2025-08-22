package org.modeart.tailor.feature.main.main.contract

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class BottomNavUiState(
    val screens: List<BottomNavScreensState> = emptyList(),
    val selectedScreen: RootBottomNavId = RootBottomNavId.Home
)


data class BottomNavScreensState(
    val id: RootBottomNavId,
    val name: StringResource? = null,
    val openScreen: () -> Unit,
    val isSelected: Boolean,
    val icon: DrawableResource? = null
)

enum class RootBottomNavId {
    Home,
    Measure,
    Note,
    Customer
}