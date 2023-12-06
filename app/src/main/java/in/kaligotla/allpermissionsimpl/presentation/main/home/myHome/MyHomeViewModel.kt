package `in`.kaligotla.allpermissionsimpl.presentation.main.home.myHome

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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
    private val repo: PermissionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var permissionsList by mutableStateOf(emptyList<Permission>())
    private var permissionsSearchList by mutableStateOf(emptyList<Permission>())

    init {
         repo.bindGetAllPermissionsFromSQLite().observeForever{
             if(it.isNotEmpty()) permissionsList = it
             else if(it.isNullOrEmpty()) onEvent(PermissionsEvent.SetPermissions)
         }
    }

    private fun onEvent(event: PermissionsEvent) {
        viewModelScope.launch {
            when (event) {
                is PermissionsEvent.SetPermissions -> {
                    Log.e("Permissions","to SQLite")
                    repo.setAllPermissionsToSQLite(PERMISSIONS_ARRAY.toList())
                }
            }
        }
    }
}

sealed class PermissionsEvent {
    object SetPermissions : PermissionsEvent()
}

data class PermissionsState(
    val permissions: List<Permission> = listOf()
)
