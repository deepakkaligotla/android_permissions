package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.location

import android.content.Context
import androidx.lifecycle.LiveData
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.LocationDetails
import kotlinx.coroutines.flow.StateFlow

interface MyLocationViewModel {
    var locations: StateFlow<List<LocationDetails>>
    fun getLocationLive(context: Context)
}