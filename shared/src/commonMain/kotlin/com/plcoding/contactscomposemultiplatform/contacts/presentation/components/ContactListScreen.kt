package com.plcoding.contactscomposemultiplatform.contacts.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListEvent
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListState

@Composable
fun ContactListScreen(
    state: ContactListState,
    newContact: Contact?,
    onEvent : (ContactListEvent) -> Unit
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ContactListEvent.OnAddNewContactClick)
                },
                shape = RoundedCornerShape(20.dp)
                )
        }
    ) {}
}