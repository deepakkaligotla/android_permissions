package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.physicalActivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.DetectedActivity

@Composable
fun ActivityRecognitionScreen() {
    var detectedActivity by remember { mutableStateOf<DetectedActivity?>(null) }

    val context = LocalContext.current

    // TODO: Handle ACTIVITY_RECOGNITION permission here

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (detectedActivity != null) {
            Text("Detected Activity: ${getActivityString(detectedActivity!!.type)}")
        } else {
            Text("No activity detected.")
        }
    }
}

private fun getActivityString(activityType: Int): String {
    return when (activityType) {
        DetectedActivity.IN_VEHICLE -> "In Vehicle"
        DetectedActivity.ON_BICYCLE -> "On Bicycle"
        DetectedActivity.ON_FOOT -> "On Foot"
        DetectedActivity.RUNNING -> "Running"
        DetectedActivity.STILL -> "Still"
        DetectedActivity.TILTING -> "Tilting"
        DetectedActivity.UNKNOWN -> "Unknown"
        DetectedActivity.WALKING -> "Walking"
        else -> "Unknown"
    }
}