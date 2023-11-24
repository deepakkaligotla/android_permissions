package `in`.kaligotla.allpermissionsimpl.data.remote

import `in`.kaligotla.allpermissionsimpl.data.domain.model.PermissionsList
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PermissionService {
    @GET("permissions")
    suspend fun getAllPermissions(): Response<PermissionsList>

    @POST("location/{id}")
    suspend fun getPermissionById(@Path("id") id: Int): Response<Permission>
}