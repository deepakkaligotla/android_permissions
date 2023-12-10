package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(eventLocation)
        parcel.writeValue(status)
        parcel.writeValue(dtStart)
        parcel.writeValue(dtEnd)
        parcel.writeString(duration)
        parcel.writeValue(allDay)
        parcel.writeValue(availability)
        parcel.writeString(rRule)
        parcel.writeValue(displayColor)
        parcel.writeValue(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventItem> {
        override fun createFromParcel(parcel: Parcel): EventItem {
            return EventItem(parcel)
        }

        override fun newArray(size: Int): Array<EventItem?> {
            return arrayOfNulls(size)
        }
    }

}