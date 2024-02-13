package com.plcoding.contactscomposemultiplatform.di

import android.content.Context
import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.database.DataBaseDriverFactory
import com.plcoding.contactscomposemultiplatform.database.ContactDatabase

actual class AppModule(
    private val context: Context
) {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightDataSource(
            db = ContactDatabase(
                driver = DataBaseDriverFactory(context).create()
            )
        )
    }
}