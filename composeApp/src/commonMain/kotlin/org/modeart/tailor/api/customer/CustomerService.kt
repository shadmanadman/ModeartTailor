package org.modeart.tailor.api.customer

import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.model.customer.CustomerProfile

interface CustomerService {
    suspend fun createCustomer(customerProfile: CustomerProfile): HttpResponse
    suspend fun updateCustomer(customerProfile: CustomerProfile): HttpResponse
    suspend fun deleteCustomer(customerId: String): HttpResponse
}