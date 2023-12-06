package `in`.kaligotla.allpermissionsimpl.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission

@Database(entities = [Permission::class, CalendarItem::class, EventItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dBPermissionDao(): PermissionDao
    abstract fun dBCalendarDao(): CalendarDao
    abstract fun dBCalendarEventDao(): CalendarEventDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "permissions")
                .fallbackToDestructiveMigration()
                .build()
    }
}