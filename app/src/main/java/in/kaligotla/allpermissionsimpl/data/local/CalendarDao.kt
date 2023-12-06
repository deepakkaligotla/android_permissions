package `in`.kaligotla.allpermissionsimpl.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar")
    fun getAllCalendars(): LiveData<List<CalendarItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCalendars(calendars: List<CalendarItem>)
}