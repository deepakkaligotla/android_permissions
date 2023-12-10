package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.contacts

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.domain.model.CallLogItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Contact
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@SuppressLint("Range")
@HiltViewModel
class MyContactsViewModel @Inject constructor(
    
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    fun getAllContactsGroupsID(context: Context) {
        val uri = ContactsContract.Contacts.CONTENT_URI
        val projection = null
        val selection = null
        val selectionArgs = null
        val sort = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC"
        val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, sort)

        while(cursor?.moveToNext()==true) {
            val contactId = cursor.getLongOrNull(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val name = cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
            val hasPhoneNumber = cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))!!.toInt()
            val phoneNumber: Map<String, String> = if (hasPhoneNumber > 0) {
                retrievePhoneNumber(contactId!!, context)
            } else mutableMapOf()

            val avatar = retrieveAvatar(contactId!!, context)
            val newContactItem = Contact(contactId.toString(), name, avatar.toString(),
                phoneNumber["Home"], phoneNumber["Mobile"], phoneNumber["Work"], null)
            if(contacts.value.isEmpty()) {
                _contacts.value += listOf(newContactItem)
            } else if(!contacts.value.contains(newContactItem)) {
                _contacts.value += listOf(newContactItem)
            }
        }
        cursor?.close()
    }

    private fun retrievePhoneNumber(contactId: Long, context: Context): Map<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()
        val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
                arrayOf(contactId.toString()),
                null
            )
        while(cursor?.moveToNext()==true) {
            when(cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))) {
                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> {
                    cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?.let {
                        result += mapOf(Pair("Home", it))
                    }
                }
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                    cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?.let {
                        result += mapOf(Pair("Mobile", it))
                    }
                }
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> {
                    cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?.let {
                        result += mapOf(Pair("Work", it))
                    }
                }
                ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM -> {
                    cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?.let {
                        result += mapOf(Pair("Custom", it))
                    }
                }
            }
        }
        return result
    }

    private fun retrieveAvatar(contactId: Long, context: Context): Uri? {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.CONTACT_ID} =? AND ${ContactsContract.Data.MIMETYPE} = '${ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE}'",
            arrayOf(contactId.toString()),
            null
        )
        return if (cursor?.moveToFirst()==true) {
            val contactUri = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,
                contactId
            )
            Uri.withAppendedPath(
                contactUri,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
            )
        } else null
    }
}