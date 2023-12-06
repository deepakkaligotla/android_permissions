package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.BodySensorBottomBar
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyBodySensors(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyBodySensorsViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val sensorStates by rememberUpdatedState(viewModel.sensorStateList)
    val pagerState = rememberPagerState()
//    val swipeRefreshState = rememberPullRefreshState(true, onRefresh =)
    val bodySensorsPermissionState = rememberPermissionState("android.permission.BODY_SENSORS")
    val bodySensorsBackgroundPermissionState = rememberPermissionState("android.permission.BODY_SENSORS_BACKGROUND")

    AllPermissionsImplTheme(appTheme = userTheme) {
        LaunchedEffect(pagerState.currentPage) {
            sensorStates.forEachIndexed { index, listener ->
                if (pagerState.currentPage == index) {
                    listener.startListening()
                } else {
                    listener.stopListening()
                }
            }
        }

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
        Scaffold(
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bodySensorsPermissionState.status.isGranted) {
                    if (bodySensorsBackgroundPermissionState.status.isGranted) {
                        Scaffold(
                            bottomBar = {
                                BodySensorBottomBar(coroutineScope, pagerState, viewModel.sensorStateList.size)
                            }
                        ) {
                            BodySensorMainContent(pagerState, viewModel.sensorStateList)
                        }
                    } else {
                        Button(onClick = {
                            bodySensorsBackgroundPermissionState.launchPermissionRequest()
                        }) {
                            Text("Grant background permission")
                        }
                    }
                } else {
                    Button(onClick = {
                        bodySensorsPermissionState.launchPermissionRequest()
                    }) {
                        Text("Grant Body Sensors permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyBodySensorsScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyBodySensorsScreen
    }
}