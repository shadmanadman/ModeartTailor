package org.modeart.tailor.api.business

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.api.safeRequest
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.ImageUploadResponse
import org.modeart.tailor.model.customer.CustomerProfile

class BusinessRepo(private val client: HttpClient) : BusinessService {
    override suspend fun businessProfile(): ApiResult<BusinessProfile> =
        safeRequest { client.get("/business/profile").body() }

    override suspend fun updateBusinessProfile(
        businessProfile: BusinessProfile,
        id: String
    ): ApiResult<Unit> =
        safeRequest {
            client.patch("/business/update-profile") { setBody(businessProfile) }
                .body()
        }

    override suspend fun createNote(
        notes: BusinessProfile.Notes
    ): ApiResult<Unit> = safeRequest {
        client.post("/business/note") { setBody(notes) }.body()
    }

    override suspend fun updateNote(
        notes: BusinessProfile.Notes
    ): ApiResult<Unit> = safeRequest {
        client.patch("/business/note/${notes.id}") { setBody(notes) }.body()
    }

    override suspend fun deleteNote(noteId: String): ApiResult<Unit> = safeRequest {
        client.delete("/business/note/$noteId")
    }


    override suspend fun getBusinessNotes(): ApiResult<List<BusinessProfile.Notes>> =
        safeRequest {
            client.get("/business/notes")
        }

    override suspend fun getBusinessCustomers(): ApiResult<List<CustomerProfile>> =
        safeRequest { client.get("/customer/customers").body() }


    override suspend fun uploadImage(byteArray: ByteArray): ApiResult<ImageUploadResponse> =
        safeRequest {
            client.post(urlString = "/upload/image") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("image", byteArray, Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=upload.png")
                            })
                        }
                    )
                )
            }.body()
        }

}