package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.LocationDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("MissingPermission")
@HiltViewModel
class MyLocationViewModelImpl @Inject constructor(): MyLocationViewModel, ViewModel() {

    private val _locations = MutableStateFlow<List<LocationDetails>>(emptyList())
    override var locations: StateFlow<List<LocationDetails>> = _locations.asStateFlow()

    override fun getLocationLive(context: Context) {
        val locationRequest = LocationRequest.Builder(1)
            .setWaitForAccurateLocation(true)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(availability: LocationAvailability) {
                super.onLocationAvailability(availability)
                Log.e("isLocationAvailable","${availability.isLocationAvailable}")
            }
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                _locations.value += listOf(LocationDetails(locationResult.locations.last().latitude, locationResult.locations.last().longitude))
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback.let {
            fusedLocationClient.requestLocationUpdates(locationRequest, it, Looper.getMainLooper())
        }
    }
}