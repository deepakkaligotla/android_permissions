package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.callLogs

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import javax.inject.Inject

@HiltViewModel
class MyCallLogsViewModel @Inject constructor(
    private val repo: PermissionRepository
) : ViewModel()  {

}