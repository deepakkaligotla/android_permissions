package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey()
    @field:SerializedName("contact_id")
    val contactId: String?,

    @field:SerializedName("contact_name")
    val contactName: String?,

    @field:SerializedName("contact_photo")
    val contactPhoto: String?,

    @field:SerializedName("contact_home_number")
    val homeNumber: String?,

    @field:SerializedName("contact_mobile_number")
    val mobileNumber: String?,

    @field:SerializedName("contact_work_number")
    val workNumber: String?,

    @field:SerializedName("contact_last_update")
    val contactLastUpdate: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contactId)
        parcel.writeString(contactName)
        parcel.writeString(contactPhoto)
        parcel.writeString(homeNumber)
        parcel.writeString(mobileNumber)
        parcel.writeString(workNumber)
        parcel.writeString(contactLastUpdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }

}