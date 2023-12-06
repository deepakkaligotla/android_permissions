package `in`.kaligotla.allpermissionsimpl.data.repository.permission

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import `in`.kaligotla.allpermissionsimpl.core.LoadDataService
import `in`.kaligotla.allpermissionsimpl.core.Resource
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import kotlinx.coroutines.flow.Flow

typealias permissionsDataFromMongo = LiveData<Resource<List<Permission>>>
typealias permissionsDataFromSQLite = LiveData<List<Permission>>
typealias permissionDataFromSQLite = LiveData<Permission>
typealias permissionCountFromSQLite = LiveData<Int>

interface PermissionRepository {
    val loadDataFromService: LoadDataService

    suspend fun setAllPermissionsToSQLite(permissionsList: List<Permission>)
    fun bindGetAllPermissionsFromMongo(): permissionsDataFromMongo
    fun bindGetAllPermissionsFromSQLite(): permissionsDataFromSQLite
    fun getPermissionCountFromSQLite(): permissionCountFromSQLite
    fun bindGetPermissionByIDFromSQLite(id: Int): permissionDataFromSQLite
    suspend fun updatePermissionByIDToSQLite(permission: Permission)
}