package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

import java.util.Date

//@Entity(tableName = "contact_group")
data class Contact(
//    @PrimaryKey()
//    @field:SerializedName("contact_id")
    val contactId: String?,
    val contactName: String?,
    val contactPhoto: String?,
    val homeNumber: String?,
    val mobileNumber: String?,
    val workNumber: String?,
    val contactLastUpdate: String?
)