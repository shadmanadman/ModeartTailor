package org.modeart.tailor.feature.main.addNewCustomer.contract

import org.modeart.tailor.model.customer.CustomerProfile

enum class NewCustomerSteps { BasicInfo, StyleFeature, SupplementaryInfo, FinalInfo, UpperBodySize, LowerBodySize, SleeveSize, }
data class NewCustomerUiState(
    val step: NewCustomerSteps = NewCustomerSteps.BasicInfo,
    val customer: CustomerProfile = CustomerProfile()
)
