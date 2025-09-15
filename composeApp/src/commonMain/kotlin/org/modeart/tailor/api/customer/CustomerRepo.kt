package org.modeart.tailor.api.customer

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.model.customer.CustomerProfile

class CustomerRepo(private val client: HttpClient) : CustomerService {
    override suspend fun createCustomer(customerProfile: CustomerProfile): HttpResponse =
        client.post("/customer") {
            setBody(customerProfile)
        }.body()

    override suspend fun updateCustomer(customerProfile: CustomerProfile): HttpResponse =
        client.patch("/customer/${customerProfile.id}") {
            setBody(customerProfile)
        }.body()

    override suspend fun deleteCustomer(customerId: String): HttpResponse =
        client.delete("/customer/$customerId").body()
}