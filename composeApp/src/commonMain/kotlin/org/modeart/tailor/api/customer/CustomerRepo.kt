package org.modeart.tailor.api.customer

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.safeRequest
import org.modeart.tailor.model.customer.CustomerCreatedSuccessResponse
import org.modeart.tailor.model.customer.CustomerProfile

class CustomerRepo(private val client: HttpClient) : CustomerService {
    override suspend fun createCustomer(customerProfile: CustomerProfile): ApiResult<CustomerCreatedSuccessResponse> =
        safeRequest {
            client.post("/customer") {
                setBody(customerProfile)
            }
        }


    override suspend fun updateCustomer(customerProfile: CustomerProfile): ApiResult<Unit> =
        safeRequest {
            client.patch("/customer/${customerProfile.id}") {
                setBody(customerProfile)
            }
        }


    override suspend fun deleteCustomer(customerId: String): HttpResponse =
        client.delete("/customer/$customerId").body()

    override suspend fun addSize(
        customerId: String,
        size: CustomerProfile.Size
    ): ApiResult<Unit> = safeRequest {
        client.post("/customer/size/$customerId") {
            setBody(size)
        }
    }

}