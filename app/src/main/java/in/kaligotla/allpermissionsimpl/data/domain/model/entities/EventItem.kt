package `in`.kaligotla.allpermissionsimpl.data.domain.model.entities

data class EventItem(
    val id: Long,
    val title: String?,
    val eventLocation: String?,
    val status: Int?,
    val dtStart: Long?,
    val dtEnd: Long?,
    val duration: String?,
    val allDay: Boolean?,
    val availability: Int?,
    val rRule: String?,
    val displayColor: Int?,
    val visible: Boolean?,
)