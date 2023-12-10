package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.sms

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.SmsItem
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.common.MySimpleDialogueBox
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MyObserver
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MySms(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MySmsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val contentObserver = MyObserver(context, Handler(Looper.getMainLooper()))
    var newSms: String? by rememberSaveable { mutableStateOf(null) }
    var smsLazyList by rememberSaveable { mutableStateOf(emptyList<SmsItem>()) }
    var showSmsBody = rememberSaveable { mutableStateOf(false) }
    var selectedSms: SmsItem? by remember { mutableStateOf(null) }
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.READ_SMS",
            "android.permission.SEND_SMS",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.RECEIVE_WAP_PUSH"
        )
    )

    DisposableEffect(Unit) {
        contentObserver.registerObserver()
        onDispose {
            contentObserver.unregisterObserver()
        }
    }

    LaunchedEffect(Unit) {

    }

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

                Column(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            text = "SMS (${if (smsLazyList.isNotEmpty()) smsLazyList.size else 0})",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    newSms = contentObserver.newItem.collectAsStateWithLifecycle().value
                    Text("New SMS: $newSms")
                }
                if (multiplePermissionsState.allPermissionsGranted) {
                    if (smsLazyList.isEmpty()) {
                        smsLazyList.toMutableList().clear()
                        viewModel.getAllSms(context)
                        smsLazyList = viewModel.smsList
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(
                                LocalConfiguration.current.screenWidthDp.dp
                            ),
                            userScrollEnabled = true,
                            modifier = Modifier
                                .padding(5.dp)
                                .height(LocalConfiguration.current.screenHeightDp.minus(290).dp),
                            contentPadding = PaddingValues(0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (smsLazyList.isNotEmpty()) {
                                items(smsLazyList.size) { index ->
                                    ElevatedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 6.dp
                                        ),
                                        modifier = Modifier
                                            .padding(3.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .padding(8.dp)
                                                .selectable(
                                                    selected = selectedSms == smsLazyList[index],
                                                    onClick = {
                                                        selectedSms = smsLazyList[index]
                                                        showSmsBody.value = true
                                                    }
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.TopStart)
                                            ) {
                                                Text(smsLazyList[index].smsAddress!!)
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                            ) {
                                                Text(smsLazyList[index].smsDate!!)
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.BottomEnd)
                                                    .background(Color.White, CircleShape)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircleOutline,
                                                    contentDescription = "Check",
                                                    tint = if (smsLazyList[index].smsSeen && !smsLazyList[index].smsRead) Color.LightGray
                                                    else if (!smsLazyList[index].smsSeen && smsLazyList[index].smsRead) Color.Red
                                                    else if (smsLazyList[index].smsRead) Color.Blue
                                                    else Color.DarkGray
                                                )
                                            }
                                            if(selectedSms == smsLazyList[index] && showSmsBody.value) {
                                                MySimpleDialogueBox(text = selectedSms?.smsBody!!) {
                                                    showSmsBody.value = false
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Text(
                            "Scroll down for more sms",
                            modifier = Modifier.padding(bottom = 90.dp)
                        )
                    }
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant SMS permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MySmsScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MySMSScreen
    }
}