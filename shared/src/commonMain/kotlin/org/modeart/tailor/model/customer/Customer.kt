package org.modeart.tailor.model.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerProfile(
    val id: String = "",
    val name: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val address: String? = "",
    val gender: CustomerGender? = CustomerGender.MALE,
    val birthday: String? = "",
    val age : String? = "",
    val sizes: Sizes? = Sizes(),
    val sizeSource: CustomerSizeSource? = CustomerSizeSource.Body,
    val sizeFreedom: CustomerSizeFreedom? = CustomerSizeFreedom.Free,
    val extraPhoto: List<String>? = emptyList(),
    val customerStyle: CustomerStyle? = CustomerStyle.CLASSIC,
    val customerBodyType: CustomerBodyForm? = CustomerBodyForm.Rectangle,
    val customerShoulderType: CustomerShoulder? = CustomerShoulder.Straight,
    val fabricSensitivity: String? = "",
    val customerColor: CustomerColor? = CustomerColor.Black,
    val isOldCustomer: Boolean? = false,
    val referredBy: String? = "",
    val upperBodySizes: UpperBodySizes? = UpperBodySizes(),
    val lowerBodySizes: LowerBodySizes? = LowerBodySizes(),
    val sleevesSizes: SleevesSizes? = SleevesSizes(),
    val overallNote: String? = "",
    val importantNote: String? = "",
    val createdAt: String? = "",
    val updatedAt: String? = "",
    val deletedAt: String? = "",
    val deleted: Boolean? = false,
    val avatar: String? = "",
    val customerOf: List<String>? = emptyList()
) {
    @Serializable
    data class Sizes(
        @SerialName("_id")
        val id: String? = "",

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