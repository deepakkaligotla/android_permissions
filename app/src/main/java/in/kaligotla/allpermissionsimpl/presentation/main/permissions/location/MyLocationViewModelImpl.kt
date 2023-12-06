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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.LocationDetails
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("MissingPermission")
@HiltViewModel
class MyLocationViewModelImpl @Inject constructor(): MyLocationViewModel, ViewModel() {

    private val _locationsList = MutableLiveData<List<LocationDetails>>()
    override var locations: LiveData<List<LocationDetails>>
        get() = _locationsList
        set(value) {

        }

    override fun getLocationLive(context: Context) {
        val locationRequest = LocationRequest.Builder(1)
            .setWaitForAccurateLocation(true)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    _locationsList.value = listOf(LocationDetails(location.latitude, location.longitude))
                    Log.e("LatLng","Latitude: "+location.latitude+", Longitude: "+location.longitude)
                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback.let {
            fusedLocationClient.requestLocationUpdates(locationRequest, it, Looper.getMainLooper())
        }
    }
}