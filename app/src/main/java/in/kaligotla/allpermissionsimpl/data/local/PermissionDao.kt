package `in`.kaligotla.allpermissionsimpl.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import `in`.kaligotla.allpermissionsimpl.core.Constants.SQLITE_COUNT_QUERY
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission

@Dao
interface PermissionDao {

    @Query("SELECT * FROM permission")
    fun getPermissions(): LiveData<List<Permission>>

    @Query("SELECT * FROM permission WHERE permissionId = :id")
    fun getPermissionById(id: Int): LiveData<Permission>

    @Query(SQLITE_COUNT_QUERY)
    fun getPermissionCount(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(permissions: List<Permission>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(permission: Permission)

    @Update()
//    @Query("UPDATE permission set permission_name = :permissionName WHERE permission_id = :permissionID")
    suspend fun update(permission: Permission)
}