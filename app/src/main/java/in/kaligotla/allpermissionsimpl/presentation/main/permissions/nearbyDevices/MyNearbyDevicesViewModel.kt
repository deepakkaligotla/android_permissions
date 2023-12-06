package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.nearbyDevices

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.registerReceiver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.BluetoothItem
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MyNearbyDevicesViewModel @Inject constructor(

) : ViewModel() {
    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var pairedDevices: Set<BluetoothDevice>? = null
    private var pairedDevicesArray = ArrayList<BluetoothItem>()
    var pairedDevicesList by mutableStateOf(emptyList<BluetoothItem>())
    private var newDevicesArray = ArrayList<BluetoothItem>()
    var newDevicesList by mutableStateOf(emptyList<BluetoothItem>())

    val receiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.R)
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val alias = device?.alias
                    val deviceHardwareAddress = device?.address
                    val bondState = when(device?.bondState) {
                        BluetoothDevice.BOND_BONDED -> "Bonded"
                        BluetoothDevice.BOND_BONDING -> "Bonding"
                        BluetoothDevice.BOND_NONE -> "None"
                        else -> "NA"
                    }
                    val deviceType = when(device?.type) {
                        BluetoothDevice.DEVICE_TYPE_LE -> "LE"
                        BluetoothDevice.DEVICE_TYPE_CLASSIC -> "Classic"
                        BluetoothDevice.DEVICE_TYPE_DUAL -> "Dual"
                        BluetoothDevice.DEVICE_TYPE_UNKNOWN -> "Unknown"
                        else -> "NA"
                    }
                    val temp = BluetoothItem(deviceName, alias, deviceHardwareAddress, bondState, deviceType)
                    newDevicesArray.add(temp)
                    newDevicesList = newDevicesArray
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun pairedBluetoothDevices(context: Context) {
        bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager!!.adapter
        pairedDevices = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val alias = device.alias
            val deviceHardwareAddress = device.address
            val bondState = when(device.bondState) {
                BluetoothDevice.BOND_BONDED -> "Bonded"
                BluetoothDevice.BOND_BONDING -> "Bonding"
                BluetoothDevice.BOND_NONE -> "None"
                else -> "NA"
            }
            val deviceType = when(device.type) {
                BluetoothDevice.DEVICE_TYPE_LE -> "LE"
                BluetoothDevice.DEVICE_TYPE_CLASSIC -> "Classic"
                BluetoothDevice.DEVICE_TYPE_DUAL -> "Dual"
                BluetoothDevice.DEVICE_TYPE_UNKNOWN -> "Unknown"
                else -> "NA"
            }
            val temp = BluetoothItem(deviceName, alias, deviceHardwareAddress, bondState, deviceType)
            pairedDevicesArray.add(temp)
        }
        pairedDevicesList = pairedDevicesArray
    }

    fun discoverNewBluetoothDevices() {

    }
}