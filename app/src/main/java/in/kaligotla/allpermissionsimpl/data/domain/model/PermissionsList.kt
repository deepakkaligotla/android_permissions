package `in`.kaligotla.allpermissionsimpl.data.domain.model

import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission

data class PermissionsList(
    val status: String,
    val data: List<Permission>
)