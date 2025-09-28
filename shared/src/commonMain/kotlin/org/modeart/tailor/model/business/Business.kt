package org.modeart.tailor.model.business

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//import org.bson.types.ObjectId

@Serializable
data class BusinessProfile(
    val id: String = "",
    val fullName: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val profilePictureUrl: String? = "",
    val businessName: String? = "",
    val city: String? = "",
    val state: String? = "",
    val plan: Plan? = Plan.NONE,
    val notes: List<Notes>? = emptyList(),
    val createdAt: String? = "",
    val updatedAt: String? = "",
    val deletedAt: String? = "",
    val deleted: Boolean? = false,
    val planEndDate: String? = ""
) {
    @Serializable
    data class Notes(
        val id: String? = "",
        val title: String,
        val content: String,
        val createdAt: String? = "",
        val updatedAt: String? = "",
        val deletedAt: String? = "",
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

@Serializable
data class RegisterRequest(
    val fullName: String,
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