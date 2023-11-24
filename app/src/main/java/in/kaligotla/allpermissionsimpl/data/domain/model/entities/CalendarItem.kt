package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

data class CalendarItem(
    val id: Long,
    val name: String?,
    val displayName: String?,
    val color: Int?,
    val visible: Boolean?,
    val syncEvents: Boolean?,
    val accountName: String?,
    val accountType: String?,
)