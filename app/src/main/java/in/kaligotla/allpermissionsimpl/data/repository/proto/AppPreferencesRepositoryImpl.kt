package `in`.kaligotla.allpermissionsimpl.data.repository.proto

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.proto.MyAppTheme
import `in`.kaligotla.allpermissionsimpl.proto.OnboardState
import java.io.IOException
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    context: Context, private var appThemeDataStore: DataStore<MyAppTheme>
) : AppPreferencesRepository {
    override suspend fun getTheme(): LiveData<MyAppTheme> {
        return appThemeDataStore.data.asLiveData()
            .switchMap { MutableLiveData(it) }
    }

    override suspend fun setTheme(theme: AppTheme) {
        try {
            appThemeDataStore.updateData {
                it.toBuilder().setUserTheme(theme).build()
            }
        } catch (e: IOException) {
            Log.e("updateAppTheme catch", "" + e)
        }
    }

    override suspend fun clearThemeState() {
        appThemeDataStore.updateData {
            it.toBuilder().clearUserTheme().build()
        }
    }

    override suspend fun resetTheme() {
        appThemeDataStore.updateData {
            MyAppTheme.getDefaultInstance()
        }
    }
}