package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BluetoothItem(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("device_name")
    val deviceName: String?,

    @field:SerializedName("alias")
    val alias: String?,

    @field:SerializedName("mac_address")
    val macAddress: String?,

    @field:SerializedName("bond_state")
    val bondState: String?,

    @field:SerializedName("device_type")
    val deviceType: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(deviceName)
        parcel.writeString(alias)
        parcel.writeString(macAddress)
        parcel.writeString(bondState)
        parcel.writeString(deviceType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BluetoothItem> {
        override fun createFromParcel(parcel: Parcel): BluetoothItem {
            return BluetoothItem(parcel)
        }

        override fun newArray(size: Int): Array<BluetoothItem?> {
            return arrayOfNulls(size)
        }
    }

}