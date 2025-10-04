package org.modeart.tailor.feature.main.measurments.contracts

import org.modeart.tailor.model.customer.CustomerProfile

enum class MeasurementStage { SelectCustomer, SelectMeasurementType }
enum class MeasurementSelectedCustomer { NewCustomer, OldCustomer }
enum class MeasurementType { FastSize, CustomSize }
data class MeasurementUiState(
    val currentStage: MeasurementStage = MeasurementStage.SelectCustomer,
    val measurementType: MeasurementType = MeasurementType.FastSize,
    val customerType: MeasurementSelectedCustomer = MeasurementSelectedCustomer.NewCustomer,
    val selectedCustomer: CustomerProfile? = null,
)