package `in`.kaligotla.allpermissionsimpl.presentation.main.home.myHome

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.core.Constants.PERMISSIONS_ARRAY
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
    private val repo: PermissionRepository
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()
    var permissionsList by mutableStateOf(emptyList<Permission>())
    private var permissionCountFromSQLite: Int? = null
    private var permissionsSearchList by mutableStateOf(emptyList<Permission>())

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(2000)
            _isRefreshing.emit(false)
        }
    }

    fun getPermissionsCount(lifecycleOwner: LifecycleOwner) = viewModelScope.launch {
        repo.getPermissionCountFromSQLite().observe(lifecycleOwner) {
            permissionCountFromSQLite = it
            if (permissionCountFromSQLite == null || permissionCountFromSQLite == 0)
                setAllPermissions()
        }
    }

    private fun setAllPermissions() = viewModelScope.launch {
        Log.e("Permissions", "to SQLite")
        repo.setAllPermissionsToSQLite(PERMISSIONS_ARRAY.toList())
    }

    fun setupObservers(lifecycleOwner: LifecycleOwner) = viewModelScope.launch {
        repo.bindGetAllPermissionsFromSQLite().observe(lifecycleOwner, Observer {
            permissionsList = it
        })

//        repo.bindGetAllPermissionsFromMongo().observe(lifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    if (!it.data.isNullOrEmpty()) {
//                        permissionsList = it.data
//                    }
//                }
//
//                Resource.Status.ERROR ->
//                    Log.e("error", "error")
//
//                Resource.Status.LOADING ->
//                    if (!it.data.isNullOrEmpty()) {
//                        permissionsList = it.data
//                    }
//            }
//        })
    }

    fun searchID(searchID: String, lifecycleOwner: LifecycleOwner) = viewModelScope.launch {
        permissionsList.toMutableList().clear()
        if (searchID.isDigitsOnly() && searchID != "" && searchID != null && searchID.toInt() < permissionsList.size) {
            repo.bindGetPermissionByIDFromSQLite(searchID.toInt())
                .observe(lifecycleOwner, Observer {
                    permissionsSearchList = listOf(it)
                })
        }
    }

    fun updatePermissionState(modifiedPermission: Permission) = viewModelScope.launch {
        repo.updatePermissionByIDToSQLite(modifiedPermission)
    }
}
