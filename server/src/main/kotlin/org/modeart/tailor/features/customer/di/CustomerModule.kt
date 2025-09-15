package org.modeart.tailor.features.customer.di

import org.modeart.tailor.dbcore.DatabaseHelper
import org.modeart.tailor.features.customer.db.CustomerDao
import org.modeart.tailor.features.customer.db.CustomerDaoImpl

object CustomerModule {
    private val database by lazy { DatabaseHelper.getDatabase() }

    fun customerDao(): CustomerDao = CustomerDaoImpl(database)
}