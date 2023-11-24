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
import kotlin.math.exp
import kotlin.math.ln

@Immutable
class RelativeHumiditySensorState internal constructor(
    val relativeHumidity: Float = 0f,
    val isAvailable: Boolean = false,
    val actualTemp: Float = 0f,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null,
) : SensorStateListener {
    val absoluteHumidity = 216.7
        .times(relativeHumidity)
        .div(100.0)
        .times(6.112)
        .times(
            exp(
                17.62
                    .times(actualTemp)
                    .div(243.12.plus(actualTemp))
            ).div(273.15.plus(actualTemp))
        )

    private val humidity = ln(relativeHumidity / 100.0) +
            (17.62 * actualTemp) / (243.12 + actualTemp)

    val dewPointTemperature = 243.12 * (humidity / (17.62 - humidity))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RelativeHumiditySensorState) return false

        if (relativeHumidity != other.relativeHumidity) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = relativeHumidity.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "HumiditySensorState(relativeHumidity=$relativeHumidity, " +
                "isAvailable=$isAvailable, " +
                "accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

@Composable
fun rememberRelativeHumiditySensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    actualTemp: Float = 0f,
    onError: (throwable: Throwable) -> Unit = {},
): RelativeHumiditySensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.RelativeHumidity,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val relativeHumiditySensorState = remember {
        mutableStateOf(
            RelativeHumiditySensorState(
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
                relativeHumiditySensorState.value = RelativeHumiditySensorState(
                    relativeHumidity = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    actualTemp = actualTemp,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return relativeHumiditySensorState.value
}
