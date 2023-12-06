package `in`.kaligotla.allpermissionsimpl.presentation.main.home.myHome

import android.content.Context
import android.os.BatteryManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import `in`.kaligotla.allpermissionsimpl.navigation.Screen.*
import `in`.kaligotla.allpermissionsimpl.presentation.common.LaunchItem
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MyHome(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyHomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val batLevel by remember { mutableStateOf(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)) }
    var lazyList by rememberSaveable { mutableStateOf(emptyList<Permission>().toMutableList()) }
//    val refreshing by viewModel.isRefreshing
//    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })
    lazyList = viewModel.permissionsList.toMutableList()

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
                            text = "Grant & check permissions",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (lazyList.isNotEmpty()) {
                    val multiplePermissionsState = rememberMultiplePermissionsState(
                        lazyList.map { permission -> permission.permissionName }.toMutableList()
                    )
                    CheckMultiplePermissions(multiplePermissionsState)
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(LocalConfiguration.current.screenWidthDp / 120),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 90.dp)
//                        .pullRefresh(pullRefreshState)
                        .testTag("myTableLazyGrid"),
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    if (lazyList.isNotEmpty()) {
                        items(lazyList) { permission ->
                            LaunchItem(permission = permission)
                        }
                    } else {
                        items(viewModel.permissionsList) { permission ->
                            LaunchItem(permission = permission)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CheckMultiplePermissions(multiplePermissionsState: MultiplePermissionsState) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (multiplePermissionsState.allPermissionsGranted) {
            Text("All permissions Granted! Thank you!")
        } else {
            Column {
                Text(
                    getTextToShowGivenPermissions(
                        multiplePermissionsState.revokedPermissions,
                        multiplePermissionsState.shouldShowRationale
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                }) {
                    Text("Grant all permissions")
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The red ")
    }

    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied, app cannot function without them."
        }
    )
    return textToShow.toString()
}

@Preview
@Composable
fun AuthScreenPreview() {
    MyHomeScreen
}