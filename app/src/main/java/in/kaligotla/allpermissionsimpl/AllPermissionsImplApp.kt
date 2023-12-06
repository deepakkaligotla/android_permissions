package `in`.kaligotla.allpermissionsimpl

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AllPermissionsImplApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}