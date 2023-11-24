package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorDelay
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorStateListener
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorType
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.rememberSensorState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Immutable
class UncalibratedLimitedAxesGyroscopeSensorState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
    val xBias: Float = 0f,
    val yBias: Float = 0f,
    val zBias: Float = 0f,
    val xAxisSupported: Boolean = false,
    val yAxisSupported: Boolean = false,
    val zAxisSupported: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null,
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UncalibratedLimitedAxesGyroscopeSensorState) return false

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
        if (xBias != other.xBias) return false
        if (yBias != other.yBias) return false
        if (zBias != other.zBias) return false
        if (xAxisSupported != other.xAxisSupported) return false
        if (yAxisSupported != other.yAxisSupported) return false
        if (zAxisSupported != other.zAxisSupported) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRotation.hashCode()
        result = 31 * result + yRotation.hashCode()
        result = 31 * result + zRotation.hashCode()
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "UncalibratedGyroscopeSensorState(xRotation=$xRotation, yRotation=$yRotation," +
                " zRotation=$zRotation," + "xBias=$xBias," + "yBias=$yBias," + "zBias=$zBias," +
                " xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported," +
                " zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun rememberUncalibratedLimitedAxesGyroscopeSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): UncalibratedLimitedAxesGyroscopeSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GyroscopeLimitedAxesUncalibrated,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val uncalibratedLimitedAxesGyroscopeSensorState =
        remember {
            mutableStateOf(
                UncalibratedLimitedAxesGyroscopeSensorState(
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            )
        }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                uncalibratedLimitedAxesGyroscopeSensorState.value =
                    UncalibratedLimitedAxesGyroscopeSensorState(
                        xRotation = sensorStateValues[0],
                        yRotation = sensorStateValues[1],
                        zRotation = sensorStateValues[2],
                        xBias = sensorStateValues[3],
                        yBias = sensorStateValues[4],
                        zBias = sensorStateValues[5],
                        isAvailable = sensorState.isAvailable,
                        accuracy = sensorState.accuracy,
                        startListeningEvents = sensorState::startListening,
                        stopListeningEvents = sensorState::stopListening
                    )
            }
        }
    )

    return uncalibratedLimitedAxesGyroscopeSensorState.value
}
