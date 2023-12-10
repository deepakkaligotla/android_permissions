package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.sms

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.SmsItem
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.getDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MySmsViewModel @Inject constructor(

) : ViewModel() {
    private var smsArray = ArrayList<SmsItem>()
    var smsList by mutableStateOf(emptyList<SmsItem>())

    fun getAllSms(context: Context) {
        val smsCursor: Cursor? = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            Telephony.Sms.DEFAULT_SORT_ORDER
        )
        smsResults(smsCursor)
    }

    @SuppressLint("Range")
    private fun smsResults(cursor: Cursor?) {
        while(cursor?.moveToNext()==true) {
            val smsAddress = cursor.getStringOrNull(cursor.getColumnIndex(Telephony.Sms.ADDRESS))
            val smsBody = cursor.getStringOrNull(cursor.getColumnIndex(Telephony.Sms.BODY))
            val smsDate = getDateTime(cursor.getStringOrNull(cursor.getColumnIndex(Telephony.Sms.DATE))?.toLong())
            val smsSeen = when(cursor.getInt(cursor.getColumnIndex(Telephony.Sms.SEEN))) {
                1 -> true
                0 -> false
                -1 -> false
                else -> false
            }
            val smsRead = when(cursor.getInt(cursor.getColumnIndex(Telephony.Sms.READ))) {
                1 -> true
                0 -> false
                -1 -> false
                else -> false
            }

            smsArray.add(SmsItem(smsAddress, smsBody, smsDate, smsSeen, smsRead))
        }
        cursor?.close()
        smsList = smsArray.toList()
    }
}