package org.modeart.tailor.model.business

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusinessProfile(
    @SerialName("_id")
    val id: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
    val businessName: String? = null,
    val city: String? = null,
    val state: String? = null,
    val plan: Plan? = null,
    val notes: List<Notes>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val deleted: Boolean? = null,
    val planEndDate: String? = null
) {
    @Serializable
    data class Notes(
        @SerialName("_id")
        val id: String? = null,
        val title: String,
        val content: String,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val deletedAt: String? = null,
        val category: NoteCategory,
    )
}

enum class Plan { MONTHLY, YEARLY, NONE }
enum class NoteCategory { PERSONAL, OTHERS, WORK }


@Serializable
data class PhoneCheckRequest(val phoneNumber: String)

@Serializable
data class PhoneCheckResponse(val exists: Boolean)

@Serializable
data class OtpRequest(val phoneNumber: String)

@Serializable
data class OtpResponse(val success: Boolean, val message: String)

@Serializable
data class RefreshTokenRequest(val refreshToken: String, val phoneNumber: String)

@Serializable
data class Tokens(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class AuthRequest(
    val phoneNumber: String,
    val otp: String
)

data class OtpData(
    val phoneNumber: String,
    val otp: String,
    val expiry: Long
)

@Serializable
data class SmsIrRequest(
    val mobile: String,
    val templateId: Int,
    val parameters: List<SmsIrParameter>
) {
    @Serializable
    data class SmsIrParameter(
        val name: String,
        val value: String
    )
}