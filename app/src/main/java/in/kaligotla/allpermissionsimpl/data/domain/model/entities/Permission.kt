package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@RequiresApi(Build.VERSION_CODES.Q)
@Entity(tableName = "permission")
data class Permission(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("permission_id")
    val permissionId: Int,

    @field:SerializedName("permission_name")
    val permissionName: String,

    @field:SerializedName("is_granted")
    val isGranted: Boolean
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readBoolean()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(permissionId)
        parcel.writeString(permissionName)
        parcel.writeByte(if (isGranted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Permission> {
        override fun createFromParcel(parcel: Parcel): Permission {
            return Permission(parcel)
        }

        override fun newArray(size: Int): Array<Permission?> {
            return arrayOfNulls(size)
        }
    }
}
