package com.plcoding.contactscomposemultiplatform.di

import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.database.DataBaseDriverFactory
import com.plcoding.contactscomposemultiplatform.database.ContactDatabase

actual class AppModule{
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightDataSource(
            db = ContactDatabase(
                driver = DataBaseDriverFactory().create()
            )
        )
    }
}