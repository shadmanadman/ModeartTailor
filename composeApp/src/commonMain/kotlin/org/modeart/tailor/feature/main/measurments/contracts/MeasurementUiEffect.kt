package org.modeart.tailor.feature.main.measurments.contracts

enum class MeasurementStage { SelectCustomer, SelectMeasurementType }
enum class MeasurementType { FastSize, CustomSize }
data class MeasurementUiEffect(
    val currentStage: MeasurementStage = MeasurementStage.SelectCustomer,
    val measurementType: MeasurementType = MeasurementType.FastSize
)