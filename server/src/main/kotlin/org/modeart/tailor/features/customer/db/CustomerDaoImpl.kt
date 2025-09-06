package org.modeart.tailor.features.customer.db

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.BsonValue
import org.bson.types.ObjectId
import org.modeart.tailor.model.customer.CustomerProfile

class CustomerDaoImpl(private val mongoDatabase: MongoDatabase) : CustomerDao {
    companion object {
        const val CUSTOMER_COLLECTION = "customer"
    }

    override suspend fun findAll(): List<CustomerProfile>? {
        TODO("Not yet implemented")
    }

    override suspend fun findById(objectId: ObjectId): CustomerProfile? {
        TODO("Not yet implemented")
    }

    override suspend fun insertOne(customer: CustomerProfile): BsonValue? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(objectId: ObjectId): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateOne(
        objectId: ObjectId,
        customer: CustomerProfile
    ): Long {
        TODO("Not yet implemented")
    }

}