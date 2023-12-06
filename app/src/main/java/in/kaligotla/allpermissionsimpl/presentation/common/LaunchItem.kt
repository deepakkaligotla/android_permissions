package `in`.kaligotla.allpermissionsimpl.presentation.common

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LaunchItem(permission: Permission) {

    val context = LocalContext.current
    var containerColor: Color?
    var contentColor: Color?

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val permissionState = rememberPermissionState(permission.permissionName)
        var textToShow: String?

        if (permissionState.status.isGranted) {
            containerColor = Color.Green
            contentColor = Color.Black
            textToShow = "Permission granted"
        } else {
            containerColor = Color.Red
            contentColor = Color.White
            textToShow = if (permissionState.status.shouldShowRationale) {
                "This permission is important for this app. Please grant the permission."
            } else {
                "Permission not granted"
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor!!, contentColor!!),
            onClick = {
                Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show()
                permissionState.launchPermissionRequest()
            }) {
            Text(
                text = permission.permissionName.replace("android.permission.", "")
                    .replace("com.android.voicemail.permission.", "")
                    .replace("com.android.launcher.permission.", "")
                    .replace("com.android.alarm.permission.", "")
                    .replace("com.android.launcher.permission.", "")
                    .replace("_", " "),
                fontSize = 10.sp
            )
        }
    }
}