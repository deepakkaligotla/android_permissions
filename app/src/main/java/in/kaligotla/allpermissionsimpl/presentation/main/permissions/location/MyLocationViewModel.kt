package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.location

import android.content.Context
import androidx.lifecycle.LiveData
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.LocationDetails

interface MyLocationViewModel {
    var locations: LiveData<List<LocationDetails>>
    fun getLocationLive(context: Context)
}