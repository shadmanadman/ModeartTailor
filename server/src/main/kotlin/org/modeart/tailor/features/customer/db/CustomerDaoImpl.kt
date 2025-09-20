package org.modeart.tailor.features.customer.db

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.types.ObjectId
import org.modeart.tailor.model.customer.CustomerProfile

class CustomerDaoImpl(private val mongoDatabase: MongoDatabase) : CustomerDao {
    companion object {
        const val CUSTOMER_COLLECTION = "customer"
    }

    override suspend fun findAll(): List<CustomerProfile>? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION).find().toList()

    override suspend fun findById(objectId: ObjectId): CustomerProfile? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .withDocumentClass<CustomerProfile>()
            .find(Filters.eq("_id", objectId))
            .firstOrNull()

    override suspend fun findByPhone(phone: String): CustomerProfile? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .withDocumentClass<CustomerProfile>()
            .find(Filters.eq("phone", phone))
            .firstOrNull()

    override suspend fun insertOne(customer: CustomerProfile): BsonValue? {
        try {
            val result =
                mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION).insertOne(
                    customer
                )

            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }

        return null
    }

    override suspend fun deleteById(objectId: ObjectId): Long {
        try {
            val result = mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
                .deleteOne(Filters.eq("_id", objectId))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }

        return 0
    }

    override suspend fun updateOne(
        objectId: ObjectId,
        customer: CustomerProfile
    ): Long {
        try {
            val query = Filters.eq("_id", objectId)
            val updates = mutableListOf<org.bson.conversions.Bson>()

            // Helper function to add update if value is not null
            fun <T> addUpdateIfNotNull(
                field: kotlin.reflect.KProperty1<CustomerProfile, T?>,
                value: T?
            ) {
                value?.let { updates.add(Updates.set(field.name, it)) }
            }

            addUpdateIfNotNull(CustomerProfile::name, customer.name)
            addUpdateIfNotNull(CustomerProfile::email, customer.email)
            addUpdateIfNotNull(CustomerProfile::phoneNumber, customer.phoneNumber)
            addUpdateIfNotNull(CustomerProfile::address, customer.address)
            addUpdateIfNotNull(CustomerProfile::gender, customer.gender)
            addUpdateIfNotNull(CustomerProfile::birthday, customer.birthday)
            addUpdateIfNotNull(CustomerProfile::sizeSource, customer.sizeSource)
            addUpdateIfNotNull(CustomerProfile::importantNote, customer.importantNote)
            addUpdateIfNotNull(CustomerProfile::sizeFreedom, customer.sizeFreedom)
            addUpdateIfNotNull(CustomerProfile::extraPhoto, customer.extraPhoto)
            addUpdateIfNotNull(CustomerProfile::customerStyle, customer.customerStyle)
            addUpdateIfNotNull(CustomerProfile::customerBodyType, customer.customerBodyType)
            addUpdateIfNotNull(CustomerProfile::customerShoulderType, customer.customerShoulderType)
            addUpdateIfNotNull(CustomerProfile::fabricSensitivity, customer.fabricSensitivity)
            addUpdateIfNotNull(CustomerProfile::customerColor, customer.customerColor)
            addUpdateIfNotNull(CustomerProfile::isOldCustomer, customer.isOldCustomer)
            addUpdateIfNotNull(CustomerProfile::referredBy, customer.referredBy)
            addUpdateIfNotNull(CustomerProfile::upperBodySizes, customer.upperBodySizes)
            addUpdateIfNotNull(CustomerProfile::lowerBodySizes, customer.lowerBodySizes)
            addUpdateIfNotNull(CustomerProfile::sleevesSizes, customer.sleevesSizes)
            addUpdateIfNotNull(CustomerProfile::overallNote, customer.overallNote)
            addUpdateIfNotNull(CustomerProfile::createdAt, customer.createdAt)
            addUpdateIfNotNull(CustomerProfile::updatedAt, customer.updatedAt)
            addUpdateIfNotNull(CustomerProfile::deletedAt, customer.deletedAt)
            addUpdateIfNotNull(CustomerProfile::deleted, customer.deleted)
            addUpdateIfNotNull(CustomerProfile::avatar, customer.avatar)
            addUpdateIfNotNull(CustomerProfile::customerOf, customer.customerOf)


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


    override suspend fun findByBusiness(businessId: String): List<CustomerProfile>? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .find(Filters.all(CustomerProfile::customerOf.name, businessId))
            .toList()

}