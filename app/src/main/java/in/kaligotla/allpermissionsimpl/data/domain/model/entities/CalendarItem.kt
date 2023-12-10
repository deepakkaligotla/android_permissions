package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(displayName)
        parcel.writeValue(color)
        parcel.writeValue(visible)
        parcel.writeValue(syncEvents)
        parcel.writeString(accountName)
        parcel.writeString(accountType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalendarItem> {
        override fun createFromParcel(parcel: Parcel): CalendarItem {
            return CalendarItem(parcel)
        }

        override fun newArray(size: Int): Array<CalendarItem?> {
            return arrayOfNulls(size)
        }
    }

}