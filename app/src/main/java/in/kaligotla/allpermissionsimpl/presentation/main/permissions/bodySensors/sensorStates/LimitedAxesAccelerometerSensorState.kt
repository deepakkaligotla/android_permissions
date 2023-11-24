package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorDelay
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorStateListener
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorType
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.rememberSensorState

@Immutable
class LimitedAxesAccelerometerSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
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
        if (other !is LimitedAxesAccelerometerSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
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
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
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
        return "LimitedAxesAccelerometerSensorState(xForce=$xForce, yForce=$yForce, " +
                "zForce=$zForce, xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported, " +
                "zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

@Composable
fun rememberLimitedAxesAccelerometerSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): LimitedAxesAccelerometerSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.AccelerometerLimitedAxes,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val accelerometerSensorState =
        remember {
            mutableStateOf(
                LimitedAxesAccelerometerSensorState(
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
                accelerometerSensorState.value = LimitedAxesAccelerometerSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    xAxisSupported = sensorStateValues[3] != 0f,
                    yAxisSupported = sensorStateValues[4] != 0f,
                    zAxisSupported = sensorStateValues[5] != 0f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return accelerometerSensorState.value
}
