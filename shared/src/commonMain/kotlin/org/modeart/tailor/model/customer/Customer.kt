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
    val age: String? = "",
    val code: Int? = 0,
    val sizes: List<Size>? = emptyList(),
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
    val customerOf: String = ""
) {
    @Serializable
    data class Size(
        val id: String? = "",
        val createdAt: String? = "",
        val type:List<SizeType> = emptyList(),
        val upperBodySizes: UpperBodySizes? = null,
        val lowerBodySizes: LowerBodySizes? = null,
        val sleevesSizes: SleevesSizes? = null,
    )

    @Serializable
    data class UpperBodySizes(
        val shoulderWidth: String? = null,
        val smallShoulder: String? = null,
        val neckCircumference: String? = null,
        val chestHeight: String? = null,
        val chestCircumference: String? = null,
        val chestDistance: String? = null,
        val frontLengthToWaist: String? = null,
        val waistCircumference: String? = null,
        val smallHipCircumference: String? = null,
        val largeHipCircumference: String? = null,
        val hipHeight: String? = null,
        val frontShoulderWidth: String? = null,
        val backShoulderWidth: String? = null,
        val backLengthNeckToWaist: String? = null
    )

    @Serializable
    data class LowerBodySizes(
        val waistbandCircumference: String? = null,
        val hipCircumference: String? = null,
        val waistToHipLength: String? = null,
        val thighCircumference: String? = null,
        val kneeCircumference: String? = null,
        val calfCircumference: String? = null,
        val ankleCircumference: String? = null,
        val inseamLength: String? = null,
        val outseamLength: String? = null,
        val waistToKneeLength: String? = null
    )

    @Serializable
    data class SleevesSizes(
        val fullSleeveLength: String? = null,
        val forearmCircumference: String? = null,
        val armCircumference: String? = null,
        val wristCircumference: String? = null,
        val sleeveHole: String? = null,
        val sleeveLengthToElbow: String? = null
    )
}

@Serializable
data class CustomerCreatedSuccessResponse(val id: String)
enum class SizeType { UpperBody, LowerBody, Sleeves}
enum class CustomerGender { MALE, FEMALE, OTHER }
enum class CustomerStyle { CLASSIC, MODERN, FIT, CASUAL, FORMAL, LOOSE }
enum class CustomerBodyForm { InvertedTriangle, Hourglass, Rectangle, Pear, Circle }
enum class CustomerShoulder { Straight, Sloping, Rounded }
enum class CustomerColor { White, Yellow, Red, Purple, Black }

enum class CustomerSizeSource { Body, Dress, Pattern, Distance }
enum class CustomerSizeFreedom { Fit, Free, WithAllowance }