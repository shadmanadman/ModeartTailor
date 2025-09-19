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
        val shoulderWidth: Double = 0.0,
        val smallShoulder: Double = 0.0,
        val neckCircumference: Double = 0.0,
        val chestHeight: Double = 0.0,
        val chestCircumference: Double = 0.0,
        val chestDistance: Double = 0.0,
        val frontLengthToWaist: Double = 0.0,
        val waistCircumference: Double = 0.0,
        val smallHipCircumference: Double = 0.0,
        val largeHipCircumference: Double = 0.0,
        val hipHeight: Double = 0.0,
        val frontShoulderWidth: Double = 0.0,
        val backShoulderWidth: Double = 0.0,
        val backLengthNeckToWaist: Double = 0.0
    )

    @Serializable
    data class LowerBodySizes(
        val waistbandCircumference: Double = 0.0,
        val hipCircumference: Double = 0.0,
        val waistToHipLength: Double = 0.0,
        val thighCircumference: Double = 0.0,
        val kneeCircumference: Double = 0.0,
        val calfCircumference: Double = 0.0,
        val ankleCircumference: Double = 0.0,
        val inseamLength: Double = 0.0,
        val outseamLength: Double = 0.0,
        val waistToKneeLength: Double = 0.0
    )

    @Serializable
    data class SleevesSizes(
        val fullSleeveLength:  Double = 0.0,
        val forearmCircumference:  Double = 0.0,
        val armCircumference:  Double = 0.0,
        val wristCircumference:  Double = 0.0,
        val sleeveHole:  Double = 0.0,
        val sleeveLengthToElbow:  Double = 0.0
    )
}

enum class CustomerGender { MALE, FEMALE, OTHER }
enum class CustomerStyle { CLASSIC, MODERN, FIT, CASUAL, FORMAL, LOOSE }
enum class CustomerBodyForm { InvertedTriangle, Hourglass, Rectangle, Pear, Circle }
enum class CustomerShoulder { Straight, Sloping, Rounded }
enum class CustomerColor { White, Yellow, Red, Purple, Black }