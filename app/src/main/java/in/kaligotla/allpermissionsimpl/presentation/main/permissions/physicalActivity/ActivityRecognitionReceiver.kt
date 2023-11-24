package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.physicalActivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognitionResult

class ActivityRecognitionReceiver(val onActivityDetected: (ActivityRecognitionResult) -> Unit) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && ActivityRecognitionResult.hasResult(intent)) {
            val result = ActivityRecognitionResult.extractResult(intent)
            if (result != null) {
                onActivityDetected(result)
            }
        }
    }
}