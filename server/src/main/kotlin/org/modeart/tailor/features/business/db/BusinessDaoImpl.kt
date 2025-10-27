package org.modeart.tailor.features.business.db

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.Document
import org.bson.types.ObjectId
import org.modeart.tailor.features.customer.db.CustomerDaoImpl.Companion.CUSTOMER_COLLECTION
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class BusinessDaoImpl(private val mongoDatabase: MongoDatabase) : BusinessDao {
    companion object {
        const val BUSINESS_COLLECTION = "business"
    }

    override suspend fun findAll(): List<BusinessProfile>? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION).find().toList()

    override suspend fun findById(id: String): BusinessProfile? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            .withDocumentClass<BusinessProfile>()
            .find(Filters.eq("id", id))
            .firstOrNull()

    override suspend fun findByPhone(phone: String): BusinessProfile? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            .find(Filters.eq("phoneNumber", phone))
            .firstOrNull()

    @OptIn(ExperimentalTime::class)
    override suspend fun insertOne(business: BusinessProfile): BsonValue? {
        try {

            val result =
                mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION).insertOne(
                    business.copy(createdAt = Clock.System.now().toString())
                )

            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }

        return null
    }

    override suspend fun deleteById(id: String): Long {
        try {
            val result = mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
                .deleteOne(Filters.eq("id", id))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }

        return 0
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateOne(
        objectId: String,
        businessProfile: BusinessProfile
    ): Long {
        try {
            val query = Filters.eq("id", objectId)
            val updates = mutableListOf<org.bson.conversions.Bson>()
            println("businessProfile: $businessProfile")
            // Helper function to add update if value is not null or empty
            fun <T> addUpdateIfNotEmpty(
                field: kotlin.reflect.KProperty1<BusinessProfile, T?>,
                value: T?
            ) {
                if (value != null && (value !is String || value.isNotEmpty())) {
                    updates.add(Updates.set(field.name, value))
                }
            }

            addUpdateIfNotEmpty(BusinessProfile::fullName, businessProfile.fullName)
            addUpdateIfNotEmpty(BusinessProfile::email, businessProfile.email)
            addUpdateIfNotEmpty(BusinessProfile::phoneNumber, businessProfile.phoneNumber)
            addUpdateIfNotEmpty(
                BusinessProfile::profilePictureUrl,
                businessProfile.profilePictureUrl
            )
            addUpdateIfNotEmpty(BusinessProfile::businessName, businessProfile.businessName)
            addUpdateIfNotEmpty(BusinessProfile::city, businessProfile.city)
            addUpdateIfNotEmpty(BusinessProfile::state, businessProfile.state)
            addUpdateIfNotEmpty(BusinessProfile::plan, businessProfile.plan)
            addUpdateIfNotEmpty(
                BusinessProfile::notes,
                businessProfile.notes
            )
            addUpdateIfNotEmpty(BusinessProfile::updatedAt, Clock.System.now().toString())


            if (updates.isEmpty()) {
                return 0
            }

            val combinedUpdates = Updates.combine(updates)
            val options = UpdateOptions().upsert(true)

            val result =
                mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
                    .updateOne(query, combinedUpdates, options)

            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }

    override suspend fun updateAvatar(
        objectId: String,
        avatarUrl: String
    ): Long {
        try {
            val query = Filters.eq("id", objectId)
            val update = Updates.set(BusinessProfile::profilePictureUrl.name, avatarUrl)
            val options = UpdateOptions().upsert(true)
            val result =
                mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
                    .updateOne(query, update, options)
            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update avatar due to an error: $e")
        }
        return 0
    }

    override suspend fun insertNote(businessId: String, note: BusinessProfile.Notes): Long? {
        return try {
            val collection = mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            val query = Filters.eq("id", businessId)
            val update =
                Updates.push(BusinessProfile::notes.name, note) // Add the note to the 'notes' array

            val result: UpdateResult = collection.updateOne(query, update)
            result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Error adding note to business $businessId: $e")
            0L
        }
    }

    override suspend fun deleteNote(businessId: String, noteId: String): Long? {
        return try {
            val collection = mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            val query = Filters.eq("id", businessId)
            // Pull the note from the 'notes' array where the 'noteId' matches
            val update = Updates.pull(
                BusinessProfile::notes.name,
                Filters.eq(BusinessProfile.Notes::id.name, noteId)
            )

            // For KMongo, it might look slightly different for pulling by a field in an array element:
            // val update = pullByFilter(BusinessProfile::notes, Filters.eq(BusinessProfile.Notes::noteId.name, noteId))
            // Or using the MongoDB driver directly:
            // val update = Updates.pull("notes", Document("noteId", noteId))


            val result: UpdateResult = collection.updateOne(query, update)
            result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Error deleting note $noteId from business $businessId: $e")
            0L
        }
    }

    override suspend fun getAllNotes(businessId: String): List<BusinessProfile.Notes>? {
        return try {
            val business = findById(businessId)
            business?.notes
        } catch (e: MongoException) {
            System.err.println("Error fetching notes for business $businessId: $e")
            null
        } catch (e: IllegalArgumentException) {
            System.err.println("Invalid businessId format: $businessId. Error: $e")
            null
        }
    }

    
    
}