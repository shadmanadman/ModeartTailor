package org.modeart.tailor.api.business

import io.ktor.client.statement.HttpResponse
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile

interface BusinessService {
    suspend fun businessProfile(businessId: String): BusinessProfile
    suspend fun updateBusinessProfile(businessProfile: BusinessProfile): HttpResponse
    suspend fun createNote(businessId: String, notes: BusinessProfile.Notes): HttpResponse
    suspend fun updateNote(businessId: String, notes: BusinessProfile.Notes): HttpResponse
    suspend fun deleteNote(noteId: String): HttpResponse

    suspend fun getBusinessNotes(businessId: String): List<BusinessProfile.Notes>

    suspend fun getBusinessCustomers(businessId: String): List<CustomerProfile>
}