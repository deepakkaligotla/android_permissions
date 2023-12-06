package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MyCamera(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyCameraViewModel = hiltViewModel()
) {
    val permissionState = rememberPermissionState("android.permission.CAMERA")

    AllPermissionsImplTheme(appTheme = userTheme) {
        Scaffold(
            modifier = Modifier.testTag("myTableTag"),
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(top = 5.dp)) {
                    Row {
                        Text(
                            text = "Camera",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (permissionState.status.isGranted) {
                    Button(onClick = {

                    }) {
                        Text("Open Camera")
                    }

                } else {
                    Button(onClick = {
                        permissionState.launchPermissionRequest()
                    }) {
                        Text("Grant Camera permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyCameraScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyCameraScreen
    }
}