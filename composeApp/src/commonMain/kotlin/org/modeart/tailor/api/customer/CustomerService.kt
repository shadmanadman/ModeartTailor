package org.modeart.tailor.api.customer

import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.model.customer.CustomerProfile

interface CustomerService {
    suspend fun createCustomer(customerProfile: CustomerProfile): ApiResult<Unit>
    suspend fun updateCustomer(customerProfile: CustomerProfile): ApiResult<Unit>
    suspend fun deleteCustomer(customerId: String): HttpResponse
}