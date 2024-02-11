package com.plcoding.contactscomposemultiplatform.contacts.presentation

import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact

sealed interface ContactListEvent {
    object OnAddNewContactClick: ContactListEvent
    object DismissContact: ContactListEvent
    data class OnFirstNameChange(val value:String): ContactListEvent
    data class OnLastNameChange(val value:String): ContactListEvent
    data class OnEmailChange(val value:String): ContactListEvent
    data class OnPhoneNumberChange(val value:String): ContactListEvent
    class OnPhotoPicked(val bytes: ByteArray): ContactListEvent
    object OnAddPhotoClicked: ContactListEvent
    object SaveContact: ContactListEvent
    data class SelectContact(val contact: Contact): ContactListEvent
    data class EditContact(val contact: Contact): ContactListEvent
    object DeleteContact: ContactListEvent
}