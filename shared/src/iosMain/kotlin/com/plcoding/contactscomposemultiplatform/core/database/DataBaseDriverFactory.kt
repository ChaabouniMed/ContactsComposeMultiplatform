package com.plcoding.contactscomposemultiplatform.core.database

import com.plcoding.contactscomposemultiplatform.database.ContactDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DataBaseDriverFactory {
    actual fun create(): SqlDriver {
        return  NativeSqliteDriver(ContactDatabase.Schema,"contact.db")
    }
}