package org.modeart.tailor.dbcore

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseHelper {
    private val client: MongoClient by lazy {
        val mongoUri = System.getenv("MONGO_URI") ?: "mongodb://localhost:27017"
        MongoClient.create(mongoUri)
    }

    fun getDatabase(): MongoDatabase {
        return client.getDatabase("ModeArtDatabase")
    }

    fun closeConnection() {
        client.close()
    }
}