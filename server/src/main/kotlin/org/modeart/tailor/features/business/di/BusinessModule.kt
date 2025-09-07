package org.modeart.tailor.features.business.di

import org.modeart.tailor.dbcore.DatabaseHelper
import org.modeart.tailor.features.business.db.BusinessDao
import org.modeart.tailor.features.business.db.BusinessDaoImpl

object BusinessModule {
    private val database by lazy { DatabaseHelper.getDatabase() }

    fun businessDao(): BusinessDao = BusinessDaoImpl(database)
}