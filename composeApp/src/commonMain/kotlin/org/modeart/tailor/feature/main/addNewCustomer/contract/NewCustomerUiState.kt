package org.modeart.tailor.feature.main.addNewCustomer.contract

import org.modeart.tailor.model.customer.CustomerProfile

enum class NewCustomerSteps { BasicInfo, StyleFeature, SupplementaryInfo, FinalInfo,FastSize, OverallSize }
data class NewCustomerUiState(
    val step: NewCustomerSteps = NewCustomerSteps.BasicInfo,
    val customer: CustomerProfile = CustomerProfile(),
    val aOldCustomerSelected: Boolean = false,
    val selectedFastSize: Int = 0
)
