package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Parcel
import android.os.Parcelable

data class LocationDetails(
    val latitude: Double?,
    val longitude: Double?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationDetails> {
        override fun createFromParcel(parcel: Parcel): LocationDetails {
            return LocationDetails(parcel)
        }

        override fun newArray(size: Int): Array<LocationDetails?> {
            return arrayOfNulls(size)
        }
    }

}