package org.modeart.tailor.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("_id")
    val id: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profilePictureUrl: String,
    val businessName: String,
    val city: String,
    val state: String,
    val plan: Plan,
    val notes: List<Notes>,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String,
    val deleted: Boolean,
    val planEndDate: String
) {
    @Serializable
    data class Notes(
        val title: String,
        val content: String,
        val createdAt: String,
        val updatedAt: String,
        val deletedAt: String,
        val category: NoteCategory,
    )
}

enum class Plan { MONTHLY, YEARLY, NONE }
enum class NoteCategory { PERSONAL, OTHERS, WORK }