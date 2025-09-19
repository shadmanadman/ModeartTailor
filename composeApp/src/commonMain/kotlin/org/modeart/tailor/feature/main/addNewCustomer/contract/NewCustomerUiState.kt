package org.modeart.tailor.feature.main.addNewCustomer.contract

enum class NewCustomerSteps{BasicInfo,StyleFeature,SupplementaryInfo,FinalInfo,UpperBodySize,LowerBodySize,SleeveSize,}
data class NewCustomerUiState(val step: NewCustomerSteps = NewCustomerSteps.BasicInfo)
