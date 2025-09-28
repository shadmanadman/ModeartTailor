package org.modeart.tailor.features.customer.db

import org.bson.BsonValue
import org.bson.types.ObjectId
import org.modeart.tailor.model.customer.CustomerProfile


interface CustomerDao {

    suspend fun findAll(): List<CustomerProfile>?

    suspend fun findById(objectId: ObjectId): CustomerProfile?

    suspend fun findByPhone(phone: String): CustomerProfile?

    suspend fun insertOne(customer: CustomerProfile): BsonValue?

    suspend fun updateAvatar(objectId: String, avatarUrl: String): Long

    suspend fun deleteById(objectId: ObjectId): Long

    suspend fun updateOne(objectId: ObjectId, customer: CustomerProfile): Long

    suspend fun findByBusiness(businessId: String): List<CustomerProfile>?
}
