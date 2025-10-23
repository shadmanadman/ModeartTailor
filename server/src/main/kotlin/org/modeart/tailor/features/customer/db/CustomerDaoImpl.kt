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
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CustomerDaoImpl(private val mongoDatabase: MongoDatabase) : CustomerDao {
    companion object {
        const val CUSTOMER_COLLECTION = "customer"
    }

    override suspend fun findAll(): List<CustomerProfile>? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION).find().toList()

    override suspend fun findById(objectId: String): CustomerProfile? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .withDocumentClass<CustomerProfile>()
            .find(Filters.eq("id", objectId))
            .firstOrNull()

    override suspend fun findByPhone(phone: String): CustomerProfile? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .withDocumentClass<CustomerProfile>()
            .find(Filters.eq("phone", phone))
            .firstOrNull()

    override suspend fun findByName(name: String): List<CustomerProfile>? =
        mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
            .find(Filters.all(CustomerProfile::name.name, name))
            .toList()

    @OptIn(ExperimentalTime::class)
    override suspend fun insertOne(customer: CustomerProfile): BsonValue? {
        try {
            val result =
                mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION).insertOne(
                    customer.copy(createdAt = Clock.System.now().toString())
                )

            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }

        return null
    }

    override suspend fun updateAvatar(
        objectId: String,
        avatarUrl: String
    ): Long {
        try {
            val query = Filters.eq("id", objectId)
            val updates = mutableListOf<org.bson.conversions.Bson>()
            updates.add(Updates.set("avatar", avatarUrl))
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

    override suspend fun deleteById(objectId: String): Long {
        try {
            val result = mongoDatabase.getCollection<CustomerProfile>(CUSTOMER_COLLECTION)
                .deleteOne(Filters.eq("id", objectId))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }

        return 0
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateOne(
        objectId: String,
        customer: CustomerProfile
    ): Long {
        try {
            val query = Filters.eq("id", objectId)
            val updates = mutableListOf<org.bson.conversions.Bson>()

            // Helper function to add update if value is not null or empty
            fun <T> addUpdateIfNotEmpty(
                field: kotlin.reflect.KProperty1<CustomerProfile, T?>,
                value: T?
            ) {
                value?.let {
                    if (it is String && it.isEmpty()) return@let
                    updates.add(Updates.set(field.name, it))
                }
            }

            addUpdateIfNotEmpty(CustomerProfile::name, customer.name)
            addUpdateIfNotEmpty(CustomerProfile::email, customer.email)
            addUpdateIfNotEmpty(CustomerProfile::phoneNumber, customer.phoneNumber)
            addUpdateIfNotEmpty(CustomerProfile::address, customer.address)
            addUpdateIfNotEmpty(CustomerProfile::gender, customer.gender)
            addUpdateIfNotEmpty(CustomerProfile::birthday, customer.birthday)
            addUpdateIfNotEmpty(CustomerProfile::age, customer.age)
            addUpdateIfNotEmpty(CustomerProfile::sizeSource, customer.sizeSource)
            addUpdateIfNotEmpty(CustomerProfile::importantNote, customer.importantNote)
            addUpdateIfNotEmpty(CustomerProfile::sizeFreedom, customer.sizeFreedom)
            addUpdateIfNotEmpty(CustomerProfile::extraPhoto, customer.extraPhoto)
            addUpdateIfNotEmpty(CustomerProfile::customerStyle, customer.customerStyle)
            addUpdateIfNotEmpty(CustomerProfile::customerBodyType, customer.customerBodyType)
            addUpdateIfNotEmpty(CustomerProfile::customerShoulderType, customer.customerShoulderType)
            addUpdateIfNotEmpty(CustomerProfile::fabricSensitivity, customer.fabricSensitivity)
            addUpdateIfNotEmpty(CustomerProfile::customerColor, customer.customerColor)
            addUpdateIfNotEmpty(CustomerProfile::isOldCustomer, customer.isOldCustomer)
            addUpdateIfNotEmpty(CustomerProfile::referredBy, customer.referredBy)
            addUpdateIfNotEmpty(CustomerProfile::upperBodySizes, customer.upperBodySizes)
            addUpdateIfNotEmpty(CustomerProfile::lowerBodySizes, customer.lowerBodySizes)
            addUpdateIfNotEmpty(CustomerProfile::sleevesSizes, customer.sleevesSizes)
            addUpdateIfNotEmpty(CustomerProfile::overallNote, customer.overallNote)
            addUpdateIfNotEmpty(CustomerProfile::updatedAt, Clock.System.now().toString())
            addUpdateIfNotEmpty(CustomerProfile::customerOf, customer.customerOf)


            if (updates.isEmpty()) {
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