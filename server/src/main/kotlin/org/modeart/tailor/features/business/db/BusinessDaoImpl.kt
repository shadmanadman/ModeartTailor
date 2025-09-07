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
import org.bson.types.ObjectId
import org.modeart.tailor.features.customer.db.CustomerDaoImpl.Companion.CUSTOMER_COLLECTION
import org.modeart.tailor.model.business.BusinessProfile
import org.modeart.tailor.model.customer.CustomerProfile

class BusinessDaoImpl(private val mongoDatabase: MongoDatabase) : BusinessDao {
    companion object {
        const val BUSINESS_COLLECTION = "business"
    }

    override suspend fun findAll(): List<BusinessProfile>? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION).find().toList()

    override suspend fun findById(objectId: ObjectId): BusinessProfile? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            .withDocumentClass<BusinessProfile>()
            .find(Filters.eq("_id", objectId))
            .firstOrNull()

    override suspend fun findByPhone(phone: String): BusinessProfile? =
        mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            .withDocumentClass<BusinessProfile>()
            .find(Filters.eq("phoneNumber", phone))
            .firstOrNull()

    override suspend fun insertOne(business: BusinessProfile): BsonValue? {
        try {
            val result =
                mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION).insertOne(
                    business
                )

            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }

        return null
    }

    override suspend fun deleteById(objectId: ObjectId): Long {
        try {
            val result = mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
                .deleteOne(Filters.eq("_id", objectId))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }

        return 0
    }

    override suspend fun updateOne(
        objectId: ObjectId,
        businessProfile: BusinessProfile
    ): Long {
        try {
            val query = Filters.eq("_id", objectId)
            val updates = mutableListOf<org.bson.conversions.Bson>()

            // Helper function to add update if value is not null
            fun <T> addUpdateIfNotNull(
                field: kotlin.reflect.KProperty1<BusinessProfile, T?>,
                value: T?
            ) {
                value?.let { updates.add(Updates.set(field.name, it)) }
            }

            addUpdateIfNotNull(BusinessProfile::fullName, businessProfile.fullName)
            addUpdateIfNotNull(BusinessProfile::email, businessProfile.email)
            addUpdateIfNotNull(BusinessProfile::phoneNumber, businessProfile.phoneNumber)
            addUpdateIfNotNull(
                BusinessProfile::profilePictureUrl,
                businessProfile.profilePictureUrl
            )
            addUpdateIfNotNull(BusinessProfile::businessName, businessProfile.businessName)
            addUpdateIfNotNull(BusinessProfile::city, businessProfile.city)
            addUpdateIfNotNull(BusinessProfile::state, businessProfile.state)
            addUpdateIfNotNull(BusinessProfile::plan, businessProfile.plan)
            addUpdateIfNotNull(
                BusinessProfile::notes,
                businessProfile.notes
            ) // For lists, this will update the entire list if 'businessProfile.notes' is not null.
            // If you need to update individual items within the list, that's a more complex scenario.
            addUpdateIfNotNull(BusinessProfile::createdAt, businessProfile.createdAt)
            addUpdateIfNotNull(BusinessProfile::updatedAt, businessProfile.updatedAt)
            addUpdateIfNotNull(BusinessProfile::deletedAt, businessProfile.deletedAt)
            addUpdateIfNotNull(BusinessProfile::deleted, businessProfile.deleted)
            addUpdateIfNotNull(BusinessProfile::planEndDate, businessProfile.planEndDate)


            if (updates.isEmpty()) {
                // No fields to update
                return 0
            }

            val combinedUpdates = Updates.combine(updates)
            val options = UpdateOptions().upsert(true)

            val result =
                mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
                    .updateOne(query, combinedUpdates, options)

            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }

    override suspend fun insertNote(businessId: String, note: BusinessProfile.Notes): Long? {
        return try {
            val collection = mongoDatabase.getCollection<BusinessProfile>(BUSINESS_COLLECTION)
            val query = Filters.eq("_id", businessId)
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
            val query = Filters.eq("_id", businessId)
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
}