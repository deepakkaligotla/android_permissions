package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.phone

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyPhone(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyPhoneViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val telephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.ANSWER_PHONE_CALLS",
            "android.permission.READ_PHONE_NUMBERS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.CALL_PHONE",
            "android.permission.ACCEPT_HANDOVER",
            "android.permission.USE_SIP",
            "com.android.voicemail.permission.ADD_VOICEMAIL"
        )
    )
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }

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
                            text = "Phone",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                if (multiplePermissionsState.allPermissionsGranted) {
                    Column(modifier = Modifier.scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollableState { delta ->
                            offset += delta
                            delta
                        }
                    )) {
                        Text("networkOperator - "+telephonyManager.networkOperator)
                        Text("networkOperatorName - "+telephonyManager.networkOperatorName)
                        Text("networkCountryIso - "+telephonyManager.networkCountryIso)
                        Text("networkSpecifier - "+telephonyManager.networkSpecifier)
                        Text("simOperator - "+telephonyManager.simOperator)
                        Text("simOperatorName - "+telephonyManager.simOperatorName)
                        Text("simCountryIso - "+telephonyManager.simCountryIso)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            Text("primaryImei - "+telephonyManager.primaryImei)
                        }
                        Text("voiceMailNumber - "+telephonyManager.voiceMailNumber)
                        Text("deviceSoftwareVersion - "+telephonyManager.deviceSoftwareVersion)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Text("manufacturerCode - "+telephonyManager.manufacturerCode)
                        }
//                        Text("allCellInfo - "+telephonyManager.allCellInfo.toString())
                        Text("dataActivity - "+telephonyManager.dataActivity.toString())
                        Text("dataState - "+telephonyManager.dataState.toString())
                        Text("isConcurrentVoiceAndDataSupported - "+telephonyManager.isConcurrentVoiceAndDataSupported)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Text("isDataCapable - "+telephonyManager.isDataCapable)
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Text("emergencyNumberList - "+telephonyManager.emergencyNumberList.toString())
                        }
                        Text("isDataEnabled - "+telephonyManager.isDataEnabled)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Text("isDataRoamingEnabled - "+telephonyManager.isDataRoamingEnabled)
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Text("isMultiSimSupported - "+telephonyManager.isMultiSimSupported)
                        }
                        Text("isWorldPhone - "+telephonyManager.isWorldPhone)
                        Text("line1Number - "+telephonyManager.line1Number)
                    }
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Phone permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPhoneScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyPhoneScreen
    }
}