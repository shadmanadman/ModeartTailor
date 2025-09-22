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
    val sizes: Sizes? = null,
    val sizeSource: CustomerSizeSource? = null,
    val sizeFreedom: CustomerSizeFreedom? = null,
    val extraPhoto: String? = null,
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
    val importantNote: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val deleted: Boolean? = null,
    val avatar: String? = null,
    val customerOf: List<String>? = null
) {
    @Serializable
    data class Sizes(
        @SerialName("_id")
        val id: String? = null,

    )
    @Serializable
    data class UpperBodySizes(
        val shoulderWidth: String = "0",
        val smallShoulder: String = "0",
        val neckCircumference: String = "0",
        val chestHeight: String = "0",
        val chestCircumference: String = "0",
        val chestDistance: String = "0",
        val frontLengthToWaist: String = "0",
        val waistCircumference: String = "0",
        val smallHipCircumference: String = "0",
        val largeHipCircumference: String = "0",
        val hipHeight: String = "0",
        val frontShoulderWidth: String = "0",
        val backShoulderWidth: String = "0",
        val backLengthNeckToWaist: String = "0"
    )

    @Serializable
    data class LowerBodySizes(
        val waistbandCircumference: String = "0",
        val hipCircumference: String = "0",
        val waistToHipLength: String = "0",
        val thighCircumference: String = "0",
        val kneeCircumference: String = "0",
        val calfCircumference: String = "0",
        val ankleCircumference: String = "0",
        val inseamLength: String = "0",
        val outseamLength: String = "0",
        val waistToKneeLength: String = "0"
    )

    @Serializable
    data class SleevesSizes(
        val fullSleeveLength: String = "0",
        val forearmCircumference: String = "0",
        val armCircumference: String = "0",
        val wristCircumference: String = "0",
        val sleeveHole: String = "0",
        val sleeveLengthToElbow: String = "0"
    )
}

enum class CustomerGender { MALE, FEMALE, OTHER }
enum class CustomerStyle { CLASSIC, MODERN, FIT, CASUAL, FORMAL, LOOSE }
enum class CustomerBodyForm { InvertedTriangle, Hourglass, Rectangle, Pear, Circle }
enum class CustomerShoulder { Straight, Sloping, Rounded }
enum class CustomerColor { White, Yellow, Red, Purple, Black }

enum class CustomerSizeSource { Body, Dress, Pattern, Distance }
enum class CustomerSizeFreedom { Fit, Free, WithAllowance }