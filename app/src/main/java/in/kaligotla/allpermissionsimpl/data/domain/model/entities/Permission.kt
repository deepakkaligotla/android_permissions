package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "permission")
data class Permission(

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("permission_id")
    var permissionId: Int,

    @field:SerializedName("permission_name")
    val permissionName: String,

    @field:SerializedName("is_granted")
    val isGranted: Boolean
)