package org.modeart.tailor.feature.main.addNewCustomer.contract

import org.modeart.tailor.feature.main.measurments.contracts.MeasurementType
import org.modeart.tailor.model.customer.CustomerProfile

enum class NewCustomerSteps { BasicInfo, StyleFeature, SupplementaryInfo, FinalInfo,FastSize, OverallSize }
data class NewCustomerUiState(
    val step: NewCustomerSteps = NewCustomerSteps.BasicInfo,
    val customer: CustomerProfile = CustomerProfile(),
    val size: CustomerProfile.Size = CustomerProfile.Size(),
    val measurementType: MeasurementType = MeasurementType.CustomSize,
    val currentBusinessId: String = "",
    val selectedFastSize: Int = 0
)
