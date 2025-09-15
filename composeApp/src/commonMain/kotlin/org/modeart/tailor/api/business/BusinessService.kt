package org.modeart.tailor.api.business

import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.api.ApiResult
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile

interface BusinessService {
    suspend fun businessProfile(): ApiResult<BusinessProfile>
    suspend fun updateBusinessProfile(businessProfile: BusinessProfile): ApiResult<Unit>
    suspend fun createNote(businessId: String, notes: BusinessProfile.Notes): ApiResult<Unit>
    suspend fun updateNote(businessId: String, notes: BusinessProfile.Notes): ApiResult<Unit>
    suspend fun deleteNote(noteId: String): ApiResult<Unit>

    suspend fun getBusinessNotes(businessId: String): ApiResult<List<BusinessProfile.Notes>>

    suspend fun getBusinessCustomers(businessId: String): ApiResult<List<CustomerProfile>>
}