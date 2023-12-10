package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable

data class SmsItem(
    val smsAddress: String?,
    val smsBody: String?,
    val smsDate: String?,
    val smsSeen: Boolean,
    val smsRead: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(smsAddress)
        parcel.writeString(smsBody)
        parcel.writeString(smsDate)
        parcel.writeByte(if (smsSeen) 1 else 0)
        parcel.writeByte(if (smsRead) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SmsItem> {
        override fun createFromParcel(parcel: Parcel): SmsItem {
            return SmsItem(parcel)
        }

        override fun newArray(size: Int): Array<SmsItem?> {
            return arrayOfNulls(size)
        }
    }

}