package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.provider.CalendarContract
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem

@HiltViewModel
class MyCalendarViewModel @Inject constructor(

) : ViewModel() {
    private var calendarEventArray = ArrayList<EventItem>()

    private val _calendarEvents = MutableStateFlow<List<EventItem>>(emptyList())
    val calendarEvents: StateFlow<List<EventItem>> = _calendarEvents.asStateFlow()
    private val _calendars = MutableStateFlow<List<CalendarItem>>(emptyList())
    val calendars: StateFlow<List<CalendarItem>> = _calendars.asStateFlow()

    init {

    }

    @SuppressLint("Range")
    fun getCalendars(context: Context) {
        val uri = CalendarContract.Calendars.CONTENT_URI
        val selection = ""
        val selectionArgs = emptyArray<String>()
        val calendarCursor = context.contentResolver.query(uri, null, selection, selectionArgs, null)
        while (calendarCursor?.moveToNext() == true) {
            _calendars.value += listOf(CalendarItem(
                id = calendarCursor.getLong(calendarCursor.getColumnIndex(CalendarContract.Calendars._ID)),
                name = calendarCursor.getStringOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)),
                displayName = calendarCursor.getStringOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.NAME)),
                color = calendarCursor.getIntOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR)),
                visible = calendarCursor.getIntOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.VISIBLE))==1,
                syncEvents = calendarCursor.getIntOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.SYNC_EVENTS))==1,
                accountName = calendarCursor.getStringOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME)),
                accountType = calendarCursor.getStringOrNull(calendarCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_TYPE))
            ))
        }
        calendarCursor?.close()
    }

    @SuppressLint("Range")
    fun getEvents(calendarId: String, context: Context) {
        calendarEventArray.clear()
        val uri = CalendarContract.Events.CONTENT_URI
        val selection = "(${CalendarContract.Events.CALENDAR_ID} = ?)"
        val selectionArgs = arrayOf(calendarId)
        val eventCursor = context.contentResolver.query(
            uri,
            null,
            selection, selectionArgs,
            null,
        )
        while (eventCursor?.moveToNext() == true) {
            val newEventItem = EventItem(
                    id = eventCursor.getLong(eventCursor.getColumnIndex(CalendarContract.Events._ID)),
                    title = eventCursor.getStringOrNull(eventCursor.getColumnIndex(CalendarContract.Events.TITLE)),
                    eventLocation = eventCursor.getStringOrNull(eventCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)),
                    status = eventCursor.getIntOrNull(eventCursor.getColumnIndex(CalendarContract.Events.STATUS)),
                    dtStart = eventCursor.getLongOrNull(eventCursor.getColumnIndex(CalendarContract.Events.DTSTART)),
                    dtEnd = eventCursor.getLongOrNull(eventCursor.getColumnIndex(CalendarContract.Events.DTEND)),
                    duration = eventCursor.getStringOrNull(eventCursor.getColumnIndex(CalendarContract.Events.DURATION)),
                    allDay = eventCursor.getIntOrNull(eventCursor.getColumnIndex(CalendarContract.Events.ALL_DAY)) == 1,
                    availability = eventCursor.getIntOrNull(eventCursor.getColumnIndex(CalendarContract.Events.AVAILABILITY)),
                    rRule = eventCursor.getStringOrNull(eventCursor.getColumnIndex(CalendarContract.Events.RRULE)),
                    displayColor = eventCursor.getIntOrNull(eventCursor.getColumnIndex(CalendarContract.Events.DISPLAY_COLOR)),
                    visible = eventCursor.getIntOrNull(eventCursor.getColumnIndex(CalendarContract.Events.VISIBLE)) == 1,
                )
            addToEvent(newEventItem)
        }
        eventCursor?.close()
        _calendarEvents.value = calendarEventArray
    }

    private fun addToEvent(newEventItem: EventItem) {
        if(calendarEventArray.isEmpty()) {
           calendarEventArray.add(newEventItem)
        } else if(!calendarEventArray.contains(newEventItem)) {
            calendarEventArray.add(newEventItem)
        }
    }
}