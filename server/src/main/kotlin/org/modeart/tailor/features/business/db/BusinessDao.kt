package org.modeart.tailor.features.business.db

import org.bson.BsonValue
import org.bson.types.ObjectId
import org.modeart.tailor.model.business.BusinessProfile

interface BusinessDao {

    suspend fun findAll(): List<BusinessProfile>?

    suspend fun findById(objectId: ObjectId): BusinessProfile?

    suspend fun findByPhone(phone: String): BusinessProfile?

    suspend fun insertOne(business: BusinessProfile): BsonValue?

    suspend fun deleteById(objectId: ObjectId): Long

    suspend fun updateOne(objectId: ObjectId, businessProfile: BusinessProfile): Long

    suspend fun insertNote(businessId: String, note: BusinessProfile.Notes): Long?

    suspend fun deleteNote(businessId: String, noteId: String): Long?

    suspend fun getAllNotes(businessId: String): List<BusinessProfile.Notes>?
}