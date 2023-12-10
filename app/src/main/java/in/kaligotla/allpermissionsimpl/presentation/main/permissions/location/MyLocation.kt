package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.location

import android.os.Build
import android.os.Handler
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.Data
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberSaveableWebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.LocationDetails
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyLocation(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyLocationViewModel = hiltViewModel<MyLocationViewModelImpl>()
) {
    val context = LocalContext.current
    val locationMultiplePermissionsState = rememberMultiplePermissionsState(
        listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION")
    )
    val locationBackgroundPermissionState = rememberPermissionState("android.permission.ACCESS_BACKGROUND_LOCATION")
    var liveLocations by rememberSaveable { mutableStateOf(emptyList<LocationDetails>()) }
    var url = rememberSaveableWebViewState()
    val navigator = rememberWebViewNavigator()

    DisposableEffect(Unit) {
        viewModel.getLocationLive(context)
        liveLocations = viewModel.locations.value
        onDispose {
            liveLocations = emptyList()
        }
    }

    AllPermissionsImplTheme(appTheme = userTheme) {
        LaunchedEffect(liveLocations, url, navigator) {
            if (url.viewState == null) {
                navigator.loadUrl("https://www.google.com/maps/")
            }
            if(liveLocations.isNotEmpty()) {
                url = WebViewState(WebContent.Url("https://www.google.com/maps/search/?api=1&query=${liveLocations.last().latitude},${liveLocations.last().longitude}"))
                navigator.loadUrl("https://www.google.com/maps/search/?api=1&query=${liveLocations.last().latitude},${liveLocations.last().longitude}")
                navigator.reload()
            }
        }

        Scaffold(
            topBar = { AppBar(drawerState = drawerState) }
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (locationMultiplePermissionsState.allPermissionsGranted) {
                    if (locationBackgroundPermissionState.status.isGranted) {
                        if(liveLocations.isEmpty()) {

                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    navigator.reload()
                                    WebView(
                                        modifier = Modifier
                                            .scale(0.85f)
                                            .width(LocalConfiguration.current.screenWidthDp.dp)
                                            .height(LocalConfiguration.current.screenHeightDp.div(2).dp),
                                        navigator = navigator,
                                        state = url,
                                        captureBackPresses = false,
                                        client = AccompanistWebViewClient(),
                                        chromeClient = AccompanistWebChromeClient(),
                                        onCreated = { it: WebView ->
                                            it.settings.javaScriptEnabled = true
                                        },
                                        onDispose = { it: WebView ->
                                            it.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                                        },
                                    )
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Latitude  > > > ${liveLocations.last().latitude}")
                                        Text("Longitude > > > ${liveLocations.last().longitude}")
                                    }
                                }
                            }
                        }
                    } else {
                        Button(onClick = {
                            locationBackgroundPermissionState.launchPermissionRequest()
                        }) {
                            Text("Grant background permission")
                        }
                    }
                } else {
                    Button(onClick = {
                        locationMultiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Location permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyLocationScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyLocationScreen
    }
}