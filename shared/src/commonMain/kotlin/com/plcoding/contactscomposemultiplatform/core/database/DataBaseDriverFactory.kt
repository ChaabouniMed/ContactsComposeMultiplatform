package com.plcoding.contactscomposemultiplatform.core.database

import com.squareup.sqldelight.db.SqlDriver

//for crating the database, cause it's diffrente between android and ios
expect class DataBaseDriverFactory {
    fun create(): SqlDriver
}