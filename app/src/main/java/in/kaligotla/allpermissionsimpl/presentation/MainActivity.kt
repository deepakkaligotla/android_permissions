package `in`.kaligotla.allpermissionsimpl.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
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
//        Log.e("onCreate","onCreate")
        setContent {
            MainCompose()
        }
        startService(Intent(this, ActivityRecognitionService::class.java))
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
//        Log.e("onRestoreInstanceState","onRestoreInstanceState")
    }

    override fun onStart() {
        super.onStart()
//        Log.e("onStart","onStart")
    }

    override fun onResume() {
        super.onResume()
//        Log.e("onResume","onResume")
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        Log.e("onBackPressed","onBackPressed")
    }

    override fun onPause() {
        super.onPause()
//        Log.e("onPause","onPause")
    }

    override fun onStop() {
        super.onStop()
//        Log.e("onStop","onStop")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
//        Log.e("onSaveInstanceState","onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.e("onDestroy","onDestroy")
    }
}