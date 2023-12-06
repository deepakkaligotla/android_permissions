package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "calendar_event")
data class EventItem(

    @PrimaryKey()
    @field:SerializedName("calendar_event_id")
    val id: Long,

    @field:SerializedName("calendar_event_title")
    val title: String?,

    @field:SerializedName("calendar_event_location")
    val eventLocation: String?,

    @field:SerializedName("calendar_event_id_status")
    val status: Int?,

    @field:SerializedName("calendar_event_id_start_date")
    val dtStart: Long?,

    @field:SerializedName("calendar_event_end_date")
    val dtEnd: Long?,

    @field:SerializedName("calendar_event_duration")
    val duration: String?,

    @field:SerializedName("calendar_event_all_day")
    val allDay: Boolean?,

    @field:SerializedName("calendar_event_availability")
    val availability: Int?,

    @field:SerializedName("calendar_event_rule")
    val rRule: String?,

    @field:SerializedName("calendar_event_display_color")
    val displayColor: Int?,

    @field:SerializedName("calendar_event_visible")
    val visible: Boolean?,
)