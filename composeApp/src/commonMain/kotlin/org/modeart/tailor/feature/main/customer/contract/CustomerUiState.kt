package org.modeart.tailor.feature.main.customer.contract

import org.modeart.tailor.feature.main.customer.CustomerFilter
import org.modeart.tailor.model.customer.CustomerProfile

data class CustomerUiState(
    val businessCustomers:List<CustomerProfile> = emptyList(),
    val customerListFilter: CustomerFilter = CustomerFilter.ALPHABETIC
)
