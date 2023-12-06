package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.calendar

import android.content.Context
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.core.Constants.CALENDAR_PROJECTION
import `in`.kaligotla.allpermissionsimpl.core.Constants.EVENT_PROJECTION
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_ACCOUNT_NAME_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_ACCOUNT_TYPE_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_ALL_DAY_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_AVAILABILITY_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_CALENDAR_COLOR_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_DISPLAY_COLOR_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_DISPLAY_NAME_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_DTEND_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_DTSTART_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_DURATION_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_EVENT_LOCATION_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_ID_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_NAME_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_RRULE_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_STATUS_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_SYNC_EVENTS_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_TITLE_INDEX
import `in`.kaligotla.allpermissionsimpl.core.Constants.PROJECTION_VISIBLE_INDEX
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import javax.inject.Inject

@HiltViewModel
class MyCalendarViewModel @Inject constructor(
    private val repo: PermissionRepository
) : ViewModel() {
    var calendarList by mutableStateOf(emptyList<CalendarItem>())
    private var calendarArray = ArrayList<CalendarItem>()
    var calendarEventList by mutableStateOf(emptyList<EventItem>())
    private var calendarEventArray = ArrayList<EventItem>()

    fun getCalendars(context: Context) {
        val uri = CalendarContract.Calendars.CONTENT_URI
        val selection = ""
        val selectionArgs = emptyArray<String>()
        val cur = context.contentResolver.query(uri, CALENDAR_PROJECTION, selection, selectionArgs, null)
        while (cur?.moveToNext() == true) {
            val calId = cur.getLong(PROJECTION_ID_INDEX)
            val displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
            val name = cur.getString(PROJECTION_NAME_INDEX)
            val color = cur.getInt(PROJECTION_CALENDAR_COLOR_INDEX)
            val visible = cur.getInt(PROJECTION_VISIBLE_INDEX)
            val syncEvents = cur.getInt(PROJECTION_SYNC_EVENTS_INDEX)
            val accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)
            val accountType = cur.getString(PROJECTION_ACCOUNT_TYPE_INDEX)
            val calendarItem = CalendarItem(
                    id = calId,
                    name = name,
                    displayName = displayName,
                    color = color,
                    visible = visible == 1,
                    syncEvents = syncEvents == 1,
                    accountName = accountName,
                    accountType = accountType,
                )
            calendarArray.add(calendarItem)
        }
        cur?.close()
        calendarList = calendarArray.toList()
    }

    fun getEvents(calendarId: String, context: Context) {
        calendarEventArray.clear()
        val uri = CalendarContract.Events.CONTENT_URI
        val selection = "(${CalendarContract.Events.CALENDAR_ID} = ?)"
        val selectionArgs = arrayOf(calendarId)
        val cur = context.contentResolver.query(
            uri,
            EVENT_PROJECTION,
            selection, selectionArgs,
            null,
        )
        while (cur?.moveToNext() == true) {
            val eventId = cur.getLong(PROJECTION_ID_INDEX)
            val title = cur.getStringOrNull(PROJECTION_TITLE_INDEX)
            val eventLocation = cur.getStringOrNull(PROJECTION_EVENT_LOCATION_INDEX)
            val status = cur.getIntOrNull(PROJECTION_STATUS_INDEX)
            val dtStart = cur.getLongOrNull(PROJECTION_DTSTART_INDEX)
            val dtEnd = cur.getLongOrNull(PROJECTION_DTEND_INDEX)
            val duration = cur.getStringOrNull(PROJECTION_DURATION_INDEX)
            val allDay = cur.getIntOrNull(PROJECTION_ALL_DAY_INDEX) == 1
            val availability = cur.getIntOrNull(PROJECTION_AVAILABILITY_INDEX)
            val rRule = cur.getStringOrNull(PROJECTION_RRULE_INDEX)
            val displayColor = cur.getIntOrNull(PROJECTION_DISPLAY_COLOR_INDEX)
            val visible = cur.getIntOrNull(PROJECTION_VISIBLE_INDEX) == 1

            val newEventItem = EventItem(
                    id = eventId,
                    title = title,
                    eventLocation = eventLocation,
                    status = status,
                    dtStart = dtStart,
                    dtEnd = dtEnd,
                    duration = duration,
                    allDay = allDay,
                    availability = availability,
                    rRule = rRule,
                    displayColor = displayColor,
                    visible = visible,
                )
            addToEventArray(newEventItem)
        }
        cur?.close()
        calendarEventList = calendarEventArray.toList()
    }

    private fun addToEventArray(newEventItem: EventItem) {
        if(calendarEventArray.isEmpty()) {
            calendarEventArray.add(newEventItem)
        } else if(!calendarEventArray.contains(newEventItem)) {
            calendarEventArray.add(newEventItem)
        }
    }
}