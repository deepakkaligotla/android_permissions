package `in`.kaligotla.allpermissionsimpl.data.remote

import javax.inject.Inject

class PermissionRemoteDataSource @Inject constructor(
    private val permissionService: PermissionService
) : BaseDataSource() {
    suspend fun getPermissions() = getResult { permissionService.getAllPermissions() }
    suspend fun getPermissionById(id: Int) = getResult { permissionService.getPermissionById(id) }
}