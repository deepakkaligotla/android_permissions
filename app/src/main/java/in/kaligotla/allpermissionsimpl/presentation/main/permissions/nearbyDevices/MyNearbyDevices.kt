package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.nearbyDevices

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@RequiresApi(34)
@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyNearbyDevices(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyNearbyDevicesViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.BLUETOOTH_SCAN",
            "android.permission.BLUETOOTH_CONNECT",
            "android.permission.BLUETOOTH_ADVERTISE",
            "android.permission.NEARBY_WIFI_DEVICES",
            "android.permission.UWB_RANGING"
        )
    )

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
                            text = "Nearby Devices",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                if (multiplePermissionsState.allPermissionsGranted) {
                    viewModel.pairedBluetoothDevices(context)
                    registerReceiver(context, viewModel.receiver, IntentFilter(BluetoothDevice.ACTION_FOUND), RECEIVER_EXPORTED)
                    Log.e("pairedDevicesList",""+viewModel.pairedDevicesList)
                    Log.e("newDevicesList",""+viewModel.newDevicesList)
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Nearby Devices permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyNearbyDevicesScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyNearbyDevicesScreen
    }
}