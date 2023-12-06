package `in`.kaligotla.allpermissionsimpl.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_event")
    fun getAllCalendarEvents(): LiveData<List<EventItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarEvents(calendars: List<EventItem>)
}