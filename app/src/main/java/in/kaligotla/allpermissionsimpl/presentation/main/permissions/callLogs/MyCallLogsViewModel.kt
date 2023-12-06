package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.callLogs

import android.content.Context
import android.provider.CallLog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.core.Constants.CALL_LOG_PROJECTION
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALL_DATE_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALL_DURATION_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALL_NAME_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALL_NUMBER_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALL_TYPE_INDEX
import `in`.kaligotla.allpermissionsimpl.data.domain.model.CallLogItem
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class MyCallLogsViewModel @Inject constructor(
    private val repo: PermissionRepository
) : ViewModel()  {
    var callLogsList by mutableStateOf(emptyList<CallLogItem>())
    private var callLogsArray = ArrayList<CallLogItem>()

    fun getCallLog(context: Context) {
        val uri = CallLog.Calls.CONTENT_URI
        val selection = ""
        val selectionArgs = emptyArray<String>()
        val callLogCursor = context.contentResolver.query(uri, CALL_LOG_PROJECTION, selection, selectionArgs, null)

        while (callLogCursor?.moveToNext()==true) {
            val callNumber: String = callLogCursor.getString(PROJECTION_CALL_NUMBER_INDEX)
            val callName: String =
                if(callLogCursor.getString(PROJECTION_CALL_NAME_INDEX).equals("")) "Unknown"
                else callLogCursor.getString(PROJECTION_CALL_NAME_INDEX)
            val callDate: String = SimpleDateFormat("dd-MMM-yyyy HH:mm").format(Date(callLogCursor.getString(PROJECTION_CALL_DATE_INDEX).toLong()))
            fun callType(): String {
                if (callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "1") return "Incoming"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "2") return "Outgoing"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "3") return "Missed"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "4") return "VoiceMail"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "5") return "Rejected"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "6") return "Blocked"
                else if(callLogCursor.getString(PROJECTION_CALL_TYPE_INDEX) == "7") return "Answered Externally"
                return "Unknown"
            }
            val callDuration: String = callLogCursor.getString(PROJECTION_CALL_DURATION_INDEX)
            val temp = CallLogItem(
                number = callNumber,
                name = callName,
                date = callDate,
                type = callType(),
                duration = callDuration
            )
            callLogsArray.add(temp)
        }
        callLogCursor?.close()
        callLogsList = callLogsArray.toList()
    }

}