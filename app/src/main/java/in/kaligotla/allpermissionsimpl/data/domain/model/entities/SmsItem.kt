package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

data class SmsItem(
    val smsAddress: String?,
    val smsBody: String?,
    val smsDate: String?,
    val smsSeen: Boolean,
    val smsRead: Boolean
)