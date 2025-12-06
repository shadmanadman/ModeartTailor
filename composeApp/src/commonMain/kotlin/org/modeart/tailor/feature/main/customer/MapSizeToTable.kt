package org.modeart.tailor.feature.main.customer

import androidx.compose.runtime.Composable
import modearttailor.composeapp.generated.resources.Res
import modearttailor.composeapp.generated.resources.ankle_circumference
import modearttailor.composeapp.generated.resources.armhole
import modearttailor.composeapp.generated.resources.back_armhole
import modearttailor.composeapp.generated.resources.back_neck_to_waist_length
import modearttailor.composeapp.generated.resources.bicep_circumference
import modearttailor.composeapp.generated.resources.calf_circumference
import modearttailor.composeapp.generated.resources.chest_circumference
import modearttailor.composeapp.generated.resources.chest_gap
import modearttailor.composeapp.generated.resources.chest_height
import modearttailor.composeapp.generated.resources.forearm_circumference
import modearttailor.composeapp.generated.resources.front_armhole
import modearttailor.composeapp.generated.resources.front_waist_length
import modearttailor.composeapp.generated.resources.full_sleeve_length
import modearttailor.composeapp.generated.resources.hip_circumference
import modearttailor.composeapp.generated.resources.hip_length
import modearttailor.composeapp.generated.resources.inseam
import modearttailor.composeapp.generated.resources.knee_circumference
import modearttailor.composeapp.generated.resources.large_hip_circumference
import modearttailor.composeapp.generated.resources.neck_circumference
import modearttailor.composeapp.generated.resources.outseam
import modearttailor.composeapp.generated.resources.shoulder_width
import modearttailor.composeapp.generated.resources.sleeve_length_to_elbow
import modearttailor.composeapp.generated.resources.small_hip_circumference
import modearttailor.composeapp.generated.resources.small_shoulder
import modearttailor.composeapp.generated.resources.thigh_circumference
import modearttailor.composeapp.generated.resources.waist_and_skirt_or_pants
import modearttailor.composeapp.generated.resources.waist_circumference
import modearttailor.composeapp.generated.resources.waist_to_hip_length
import modearttailor.composeapp.generated.resources.waist_to_knee_length
import modearttailor.composeapp.generated.resources.wrist_circumference
import org.jetbrains.compose.resources.stringResource
import org.modeart.tailor.model.customer.CustomerProfile

@Composable
fun mapSleeveSizeToSizeTable(sleevesSizes: CustomerProfile.SleevesSizes):List<SizeTable>{
    return listOf(
        SizeTable(
            name = stringResource(Res.string.full_sleeve_length),
            value = sleevesSizes.fullSleeveLength
        ),
        SizeTable(
            name = stringResource(Res.string.forearm_circumference),
            value = sleevesSizes.forearmCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.bicep_circumference),
            value = sleevesSizes.armCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.wrist_circumference),
            value = sleevesSizes.wristCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.armhole),
            value = sleevesSizes.sleeveHole
        ),
        SizeTable(
            name = stringResource(Res.string.sleeve_length_to_elbow),
            value = sleevesSizes.sleeveLengthToElbow
        )
    )
}
@Composable
fun mapLowerBodySizeToSizeTable(lowerBodySizes: CustomerProfile.LowerBodySizes): List<SizeTable>{
    return listOf(
        SizeTable(
            name = stringResource(Res.string.waist_and_skirt_or_pants),
            value = lowerBodySizes.waistbandCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.hip_circumference),
            value = lowerBodySizes.hipCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.waist_to_hip_length),
            value = lowerBodySizes.waistToHipLength
        ),
        SizeTable(
            name = stringResource(Res.string.thigh_circumference),
            value = lowerBodySizes.thighCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.knee_circumference),
            value = lowerBodySizes.kneeCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.calf_circumference),
            value = lowerBodySizes.calfCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.ankle_circumference),
            value = lowerBodySizes.ankleCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.inseam),
            value = lowerBodySizes.inseamLength
        ),
        SizeTable(
            name = stringResource(Res.string.outseam),
            value = lowerBodySizes.outseamLength
        ),
        SizeTable(
            name = stringResource(Res.string.waist_to_knee_length),
            value = lowerBodySizes.waistToKneeLength
        )
    )
}

@Composable
fun mapUpperBodySizeToSizeTable(upperBodySizes: CustomerProfile.UpperBodySizes): List<SizeTable> {

    return listOf(
        SizeTable(
            name = stringResource(Res.string.shoulder_width),
            value = upperBodySizes.shoulderWidth
        ),
        SizeTable(
            name = stringResource(Res.string.small_shoulder),
            value = upperBodySizes.smallShoulder
        ),
        SizeTable(
            name = stringResource(Res.string.neck_circumference),
            value = upperBodySizes.neckCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.chest_height),
            value = upperBodySizes.chestHeight
        ),
        SizeTable(
            name = stringResource(Res.string.chest_circumference),
            value = upperBodySizes.chestCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.chest_gap),
            value = upperBodySizes.chestDistance
        ),
        SizeTable(
            name = stringResource(Res.string.front_waist_length),
            value = upperBodySizes.frontLengthToWaist
        ),
        SizeTable(
            name = stringResource(Res.string.waist_circumference),
            value = upperBodySizes.waistCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.small_hip_circumference),
            value = upperBodySizes.smallHipCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.large_hip_circumference),
            value = upperBodySizes.largeHipCircumference
        ),
        SizeTable(
            name = stringResource(Res.string.hip_length),
            value = upperBodySizes.hipHeight
        ),
        SizeTable(
            name = stringResource(Res.string.front_armhole),
            value = upperBodySizes.frontShoulderWidth
        ),
        SizeTable(
            name = stringResource(Res.string.back_armhole),
            value = upperBodySizes.backShoulderWidth
        ),
        SizeTable(
            name = stringResource(Res.string.back_neck_to_waist_length),
            value = upperBodySizes.backLengthNeckToWaist
        )
    )

}