package org.modeart.tailor.model.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerProfile(
    @SerialName("_id")
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val gender: CustomerGender? = null,
    val birthday: String? = null,
    val customerStyle: CustomerStyle? = null,
    val customerBodyType: CustomerBodyForm? = null,
    val customerShoulderType: CustomerShoulder? = null,
    val fabricSensitivity: String? = null,
    val customerColor: CustomerColor? = null,
    val isOldCustomer: Boolean? = null,
    val referredBy: String? = null,
    val upperBodySizes: UpperBodySizes? = null,
    val lowerBodySizes: LowerBodySizes? = null,
    val sleevesSizes: SleevesSizes? = null,
    val overallNote: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val deleted: Boolean? = null,
    val avatar: String? = null,
    val customerOf: List<String>? = null
) {
    @Serializable
    data class UpperBodySizes(
        val shoulderWidth: Double,
        val smallShoulder: Double,
        val neckCircumference: Double,
        val chestHeight: Double,
        val chestCircumference: Double,
        val chestDistance: Double,
        val frontLengthToWaist: Double,
        val waistCircumference: Double,
        val smallHipCircumference: Double,
        val largeHipCircumference: Double,
        val hipHeight: Double,
        val frontShoulderWidth: Double,
        val backShoulderWidth: Double,
        val backLengthNeckToWaist: Double
    )

    @Serializable
    data class LowerBodySizes(
        val waistbandCircumference: Double,
        val hipCircumference: Double,
        val waistToHipLength: Double,
        val thighCircumference: Double,
        val kneeCircumference: Double,
        val calfCircumference: Double,
        val ankleCircumference: Double,
        val inseamLength: Double,
        val outseamLength: Double,
        val waistToKneeLength: Double
    )

    @Serializable
    data class SleevesSizes(
        val fullSleeveLength: String,
        val forearmCircumference: String,
        val armCircumference: String,
        val wristCircumference: String,
        val sleeveHole: String,
        val sleeveLengthToElbow: String
    )
}

enum class CustomerGender { MALE, FEMALE, OTHER }
enum class CustomerStyle { CLASSIC, MODERN, FIT, CASUAL, FORMAL, LOOSE }
enum class CustomerBodyForm { InvertedTriangle, Hourglass, Rectangle, Pear, Circle }
enum class CustomerShoulder { Straight, Sloping, Rounded }
enum class CustomerColor { White, Yellow, Red, Purple, Black }