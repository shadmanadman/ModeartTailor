package org.modeart.tailor.feature.main.profile.contract

import org.modeart.tailor.model.business.BusinessProfile

data class ProfileUiState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val businessName: String ="",
    val address: String = "",
    val avatar:String = "",
    val customerCount: Int = 0,
)