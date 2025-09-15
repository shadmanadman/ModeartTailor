package org.modeart.tailor.api.business

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.safeRequest
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile

class BusinessRepo(private val client: HttpClient) : BusinessService {
    override suspend fun businessProfile(businessId: String): ApiResult<BusinessProfile> =
        safeRequest { client.get("/business/$businessId").body() }

    override suspend fun updateBusinessProfile(businessProfile: BusinessProfile): HttpResponse =
        client.patch("/business/${businessProfile.id}").body()

    override suspend fun createNote(
        businessId: String,
        notes: BusinessProfile.Notes
    ): HttpResponse =
        client.post("/business/${businessId}/note") { setBody(notes) }.body()

    override suspend fun updateNote(
        businessId: String,
        notes: BusinessProfile.Notes
    ): HttpResponse =
        client.patch("/business/${businessId}/note/${notes.id}") { setBody(notes) }.body()

    override suspend fun deleteNote(noteId: String): HttpResponse =
        client.delete("/business/note/$noteId")

    override suspend fun getBusinessNotes(businessId: String): ApiResult<List<BusinessProfile.Notes>> =
        safeRequest {
            client.get("/business/$businessId/notes")
        }

    override suspend fun getBusinessCustomers(businessId: String): ApiResult<List<CustomerProfile>> =
        client.get("/customer/$businessId").body()
}