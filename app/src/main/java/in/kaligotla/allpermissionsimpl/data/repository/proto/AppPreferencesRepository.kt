package `in`.kaligotla.allpermissionsimpl.data.repository.proto

import androidx.lifecycle.LiveData
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.proto.MyAppTheme

interface AppPreferencesRepository {
    suspend fun getTheme(): LiveData<MyAppTheme>
    suspend fun setTheme(theme: AppTheme)
    suspend fun resetTheme()
    suspend fun clearThemeState()
}