package org.modeart.tailor.feature.main.home.contract

import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile

data class HomeUiState(
    val fullName: String = "",
    val phoneNumber: String = "",
    val avatar: String = "",
    val customerCount: String = "0",
    val thisMonthCustomer: String = "0",
    val searchQuery: String = "",
    val latestNotes:List<BusinessProfile.Notes> = emptyList(),
    val latestCustomers:List<CustomerProfile> = emptyList()
)
