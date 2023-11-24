package `in`.kaligotla.allpermissionsimpl.data.repository.permission

import android.os.Build
import androidx.annotation.RequiresApi
import `in`.kaligotla.allpermissionsimpl.core.LoadDataService
import `in`.kaligotla.allpermissionsimpl.core.performGetOperation
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import `in`.kaligotla.allpermissionsimpl.data.local.PermissionDao
import `in`.kaligotla.allpermissionsimpl.data.remote.PermissionRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.Q)
@Singleton
class PermissionRepositoryImpl @Inject constructor(
    loadDataService: LoadDataService,
    private val remoteDataSource: PermissionRemoteDataSource,
    private val localDataSource: PermissionDao
) : PermissionRepository {
    override val loadDataFromService: LoadDataService = loadDataService

    override suspend fun setAllPermissionsToSQLite(permissionsList: List<Permission>) {
        localDataSource.insertAll(permissionsList)
    }

    override fun bindGetAllPermissionsFromMongo(): permissionsDataFromMongo {
        return performGetOperation(
            databaseQuery = { localDataSource.getPermissions() },
            networkCall = { remoteDataSource.getPermissions() },
            saveCallResult = { localDataSource.insertAll(it.data) })
    }

    override fun bindGetAllPermissionsFromSQLite(): permissionsDataFromSQLite {
        return localDataSource.getPermissions()
    }

    override fun getPermissionCountFromSQLite(): permissionCountFromSQLite {
        return localDataSource.getPermissionCount()
    }

    override fun bindGetPermissionByIDFromSQLite(pincode: Int): permissionDataFromSQLite {
        return localDataSource.getPermissionById(pincode)
    }

    override suspend fun updatePermissionByIDToSQLite(permission: Permission) {
        localDataSource.update(permission)
    }
}