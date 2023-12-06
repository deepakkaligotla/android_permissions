package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "calendar")
data class CalendarItem(

    @PrimaryKey()
    @field:SerializedName("calendar_id")
    val id: Long,

    @field:SerializedName("calendar_name")
    val name: String?,

    @field:SerializedName("calendar_display_name")
    val displayName: String?,

    @field:SerializedName("calendar_color")
    val color: Int?,

    @field:SerializedName("calendar_visible")
    val visible: Boolean?,

    @field:SerializedName("calendar_sync_events")
    val syncEvents: Boolean?,

    @field:SerializedName("calendar_account_name")
    val accountName: String?,

    @field:SerializedName("calendar_account_type")
    val accountType: String?,
)