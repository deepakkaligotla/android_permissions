package `in`.kaligotla.allpermissionsimpl.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import dagger.hilt.android.AndroidEntryPoint
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.physicalActivity.ActivityRecognitionService

@RequiresApi(Build.VERSION_CODES.O)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainCompose()
        }
        startService(Intent(this, ActivityRecognitionService::class.java))
    }
}