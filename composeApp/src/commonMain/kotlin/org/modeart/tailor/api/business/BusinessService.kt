package org.modeart.tailor.api.business

import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.business.ImageUploadResponse
import org.modeart.tailor.model.customer.CustomerProfile

interface BusinessService {
    suspend fun businessProfile(): ApiResult<BusinessProfile>
    suspend fun updateBusinessProfile(businessProfile: BusinessProfile,id:String): ApiResult<Unit>
    suspend fun createNote(notes: BusinessProfile.Notes): ApiResult<Unit>
    suspend fun updateNote(notes: BusinessProfile.Notes): ApiResult<Unit>
    suspend fun deleteNote(noteId: String): ApiResult<Unit>

    suspend fun getBusinessNotes(): ApiResult<List<BusinessProfile.Notes>>

    suspend fun getBusinessCustomers(): ApiResult<List<CustomerProfile>>

    suspend fun uploadImage(byteArray: ByteArray): ApiResult<ImageUploadResponse>
}