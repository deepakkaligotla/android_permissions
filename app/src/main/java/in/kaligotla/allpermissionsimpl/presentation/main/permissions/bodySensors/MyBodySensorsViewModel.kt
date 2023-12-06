package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.repository.permission.PermissionRepository
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorStateListener
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberAmbientTemperatureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGameRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGeomagneticRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGravitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeadTrackerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeadingSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeartBeatSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeartRateSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHingeAngleSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLightSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLimitedAxesGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLinearAccelerationSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLowLatencyOffBodyDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberMagneticFieldSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberMotionDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberPressureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberProximitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberSignificantMotionSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStationaryDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStepCounterSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStepDetectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedLimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedMagneticFieldSensorState
import javax.inject.Inject

@HiltViewModel
class MyBodySensorsViewModel @Inject constructor(
    private val repo: PermissionRepository
) : ViewModel() {
    val sensorStateList: List<SensorStateListener>
        @SuppressLint("NewApi")
        @Composable
        get() = listOf(
            rememberAccelerometerSensorState(),
            rememberMagneticFieldSensorState(),
            rememberGyroscopeSensorState(),
            rememberLightSensorState(),
            rememberPressureSensorState(),
            rememberProximitySensorState(),
            rememberGravitySensorState(),
            rememberLinearAccelerationSensorState(),
            rememberRotationVectorSensorState(),
            rememberAmbientTemperatureSensorState(),
            rememberUncalibratedMagneticFieldSensorState(),
            rememberGameRotationVectorSensorState(),
            rememberUncalibratedGyroscopeSensorState(),
            rememberSignificantMotionSensorState(onMotionEvent = {}),
            rememberStepDetectorSensorState(),
            rememberStepCounterSensorState(),
            rememberGeomagneticRotationVectorSensorState(),
            rememberHeartRateSensorState(),
            rememberStationaryDetectSensorState(),
            rememberMotionDetectSensorState(),
            rememberHeartBeatSensorState(),
            rememberLowLatencyOffBodyDetectSensorState(),
            rememberUncalibratedAccelerometerSensorState(),
            rememberHingeAngleSensorState(),
            rememberHeadTrackerSensorState(),
            rememberLimitedAxesAccelerometerSensorState(),
            rememberLimitedAxesGyroscopeSensorState(),
            rememberUncalibratedLimitedAxesAccelerometerSensorState(),
            rememberHeadingSensorState()
        )

}