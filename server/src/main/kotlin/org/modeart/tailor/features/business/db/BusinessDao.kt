package org.modeart.tailor.features.business.db

import org.bson.BsonValue
import org.bson.Document
import org.bson.types.ObjectId
import org.modeart.tailor.model.business.BusinessProfile

interface BusinessDao {

    suspend fun findAll(): List<BusinessProfile>?

    suspend fun findById(id: String): BusinessProfile?

    suspend fun findByPhone(phone: String): BusinessProfile?

    suspend fun insertOne(business: BusinessProfile): BsonValue?

    suspend fun deleteById(id: String): Long

    suspend fun updateOne(objectId: String, businessProfile: BusinessProfile): Long

    suspend fun updateAvatar(objectId: String, avatarUrl: String): Long

    suspend fun insertNote(businessId: String, note: BusinessProfile.Notes): Long?

    suspend fun deleteNote(businessId: String, noteId: String): Long?

    suspend fun getAllNotes(businessId: String): List<BusinessProfile.Notes>?
}