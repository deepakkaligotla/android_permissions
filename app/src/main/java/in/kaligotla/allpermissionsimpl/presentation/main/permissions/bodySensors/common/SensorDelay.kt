package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common

import android.hardware.SensorManager

enum class SensorDelay {
    Fastest,
    Game,
    UI,
    Normal;

    internal fun toAndroidSensorDelay(): Int {
        return when (this) {
            Fastest -> SensorManager.SENSOR_DELAY_FASTEST
            Game -> SensorManager.SENSOR_DELAY_GAME
            UI -> SensorManager.SENSOR_DELAY_UI
            Normal -> SensorManager.SENSOR_DELAY_NORMAL
        }
    }
}