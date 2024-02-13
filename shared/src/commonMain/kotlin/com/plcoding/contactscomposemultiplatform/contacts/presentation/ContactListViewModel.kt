package com.plcoding.contactscomposemultiplatform.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val contactDataSource: ContactDataSource
): ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(20)
    ) {
        state, contacts, recentContacts ->
            state.copy(
                contacts = contacts,
                recentlyAddedContact = recentContacts,
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null) //bch nestaamlouh wa9t nzidou wala nmodifiw contact, bch nestha9ouh f taamir l formulaire bch kif l user yekteb harf direct narah w maysirech delay sghir khatr kif ndakhlou maa l combine yomkn ysir delay khatrhom barcha data
        private set

    fun onEvent(event: ContactListEvent) {
        when(event) {
            ContactListEvent.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let{id ->
                        _state.update { it.copy(
                            isSelectedContactSheetOpen = false,
                        ) }
                        contactDataSource.deleteContact(id)
                        delay(300L)  // for animation time
                        _state.update { it.copy(
                            selectedContact = null
                        ) }
                    }
                }
            }
            ContactListEvent.DismissContact -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isSelectedContactSheetOpen = false,
                        isAddContactSheetOpen = false,
                        firstNameError = null,
                        lastNameError = null,
                        emailError = null,
                        phoneNumberError = null,
                    ) }
                    delay(300L) // for animation time
                    newContact = null
                    _state.update { it.copy(
                        selectedContact = null
                    )}
                }
            }
            is ContactListEvent.EditContact -> {
                _state.update { it.copy(
                    selectedContact = null,
                    isAddContactSheetOpen = true,
                    isSelectedContactSheetOpen = false
                )}
                newContact = event.contact
            }
            ContactListEvent.OnAddNewContactClick -> {
                _state.update { it.copy(
                    isAddContactSheetOpen = true
                ) }
                newContact = Contact(
                    id = null,
                    firstName = "",
                    lastName = "",
                    email = "",
                    phoneNumber = "",
                    photoByte = null

                )
            }

            is ContactListEvent.OnEmailChange -> {
                newContact = newContact?.copy(
                    email = event.value
                )
            }
            is ContactListEvent.OnFirstNameChange -> {
                newContact = newContact?.copy(
                    firstName = event.value
                )
            }
            is ContactListEvent.OnLastNameChange -> {
                newContact = newContact?.copy(
                    lastName = event.value
                )
            }
            is ContactListEvent.OnPhoneNumberChange -> {
                newContact = newContact?.copy(
                    phoneNumber = event.value
                )
            }
            is ContactListEvent.OnPhotoPicked -> {
                newContact = newContact?.copy(
                    photoByte = event.bytes
                )
            }
            //matensechh tpushi ki tkamel hedha bch ya9aad keyenou template
            ContactListEvent.SaveContact -> {
                newContact?.let {contact  ->
                    val result = ContactValidator.validateContact(contact)
                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError
                    )

                    if(errors.isEmpty()){
                        _state.update { it.copy(
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            phoneNumberError = null,
                            emailError = null
                        ) }
                        viewModelScope.launch {
                            contactDataSource.insertContact(contact)
                            delay(3000L)
                            newContact = null
                        }
                    } else {
                        _state.update { it.copy(
                            firstNameError = result.firstNameError,
                            lastNameError = result.lastNameError,
                            emailError = result.emailError,
                            phoneNumberError = result.phoneNumberError
                        ) }
                    }
                }
            }
            is ContactListEvent.SelectContact -> {
                _state.update { it.copy(
                    selectedContact = event.contact,
                    isSelectedContactSheetOpen = true
                ) }
            }
            else -> Unit
        }
    }

}
