package org.modeart.tailor.model.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerProfile(
    @SerialName("_id")
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val gender: CustomerGender,
    val birthday: String,
    val customerStyle: CustomerStyle,
    val customerBodyType: CustomerBodyForm,
    val customerShoulderType: CustomerShoulder,
    val fabricSensitivity: String,
    val customerColor: CustomerColor,
    val isOldCustomer: Boolean,
    val referredBy: String,
    val upperBodySizes: UpperBodySizes,
    val lowerBodySizes: LowerBodySizes,
    val sleevesSizes: SleevesSizes,
    val overallNote: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String,
    val deleted: Boolean,
    val avatar: String,
    val customerOf: List<String>
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