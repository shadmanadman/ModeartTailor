package org.modeart.tailor.dbcore

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseHelper {
    private val client: MongoClient by lazy {
        MongoClient.create("mongodb://localhost:27017")
    }

    fun getDatabase(): MongoDatabase {
        return client.getDatabase("ModeArtDatabase")
    }

    fun closeConnection() {
        client.close()
    }
}