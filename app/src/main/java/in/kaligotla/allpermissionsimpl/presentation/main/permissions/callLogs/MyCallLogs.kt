package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.callLogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.data.domain.model.CallLogItem
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MyCallLogs(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyCallLogsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.READ_CALL_LOG",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.PROCESS_OUTGOING_CALLS"
        )
    )

    var callLogslazyList by rememberSaveable { mutableStateOf(emptyList<CallLogItem>()) }
    var callTypelazyList by rememberSaveable { mutableStateOf(emptyMap<String, List<CallLogItem>>()) }
    val callTypePagerState = rememberPagerState(pageCount = { callTypelazyList.size })
    val callLogsPagerState = rememberPagerState(pageCount = { callTypelazyList.keys.size })

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
                            text = "Call Logs",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                if (multiplePermissionsState.allPermissionsGranted) {
                    if (callLogslazyList.isNullOrEmpty()) {
                        callLogslazyList.toMutableList().clear()
                        viewModel.getCallLog(context)
                        callLogslazyList = viewModel.callLogs.collectAsStateWithLifecycle().value
                        callTypelazyList += mapOf(
                            Pair(
                                "Incoming",
                                callLogslazyList.filter { it -> it.type == "Incoming" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Outgoing",
                                callLogslazyList.filter { it -> it.type == "Outgoing" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Missed",
                                callLogslazyList.filter { it -> it.type == "Missed" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "VoiceMail",
                                callLogslazyList.filter { it -> it.type == "VoiceMail" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Rejected",
                                callLogslazyList.filter { it -> it.type == "Rejected" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Blocked",
                                callLogslazyList.filter { it -> it.type == "Blocked" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Answered Externally",
                                callLogslazyList.filter { it -> it.type == "Answered Externally" })
                        )
                        callTypelazyList += mapOf(
                            Pair(
                                "Unknown",
                                callLogslazyList.filter { it -> it.type == "Unknown" })
                        )
                    } else {
                        HorizontalPager(
                            modifier = Modifier
                                .size(
                                    width = LocalConfiguration.current.screenWidthDp.dp,
                                    height = LocalConfiguration.current.screenHeightDp.minus(300).dp
                                ),
                            state = callLogsPagerState,
                            contentPadding = PaddingValues(1.dp),
                            pageSize = PageSize.Fixed(
                                LocalConfiguration.current.screenWidthDp.minus(
                                    50
                                ).dp
                            ),
                            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                                callTypePagerState,
                                Orientation.Vertical
                            )
                        ) { index ->
                            ElevatedCard(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Gray
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = callTypelazyList.keys.toList()[index],
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(10.dp),
                                        textAlign = TextAlign.Justify,
                                        color = Color.Green
                                    )
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LazyVerticalGrid(
                                            columns = GridCells.Fixed(1),
                                            modifier = Modifier.padding(5.dp),
                                            contentPadding = PaddingValues(0.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            if (callTypelazyList.isNotEmpty()) {
                                                if (callTypelazyList.containsKey(callTypelazyList.keys.toList()[index])) {
                                                    items(callTypelazyList[callTypelazyList.keys.toList()[index]]!!.toMutableList()) { callItem ->
                                                        ElevatedCard(
                                                            colors = CardDefaults.cardColors(
                                                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                                            ),
                                                            elevation = CardDefaults.cardElevation(
                                                                defaultElevation = 6.dp
                                                            ),
                                                            modifier = Modifier
                                                                .size(
                                                                    width = 100.dp,
                                                                    height = 80.dp
                                                                )
                                                                .padding(1.dp),
                                                            content = {
                                                                Column(
                                                                    modifier = Modifier
                                                                        .padding(1.dp)
                                                                        .fillMaxWidth()
                                                                        .testTag("Key"),
                                                                    verticalArrangement = Arrangement.Center,
                                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                                ) {
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .padding(1.dp)
                                                                            .fillMaxWidth()
                                                                            .wrapContentSize(
                                                                                Alignment.Center
                                                                            )
                                                                            .testTag("Key"),
                                                                        horizontalArrangement = Arrangement.Center,
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Text(
                                                                            text = callItem.name,
                                                                            fontSize = 20.sp,
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.padding(
                                                                                10.dp
                                                                            )
                                                                        )
                                                                        Text(
                                                                            text = callItem.number,
                                                                            fontSize = 16.sp,
                                                                        )
                                                                    }
                                                                    Row(
                                                                        modifier = Modifier
                                                                            .padding(1.dp)
                                                                            .fillMaxWidth()
                                                                            .wrapContentSize(
                                                                                Alignment.Center
                                                                            )
                                                                            .testTag("Key"),
                                                                        horizontalArrangement = Arrangement.Center,
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Text(
                                                                            text = callItem.date,
                                                                            fontSize = 16.sp,
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.padding(
                                                                                10.dp
                                                                            )
                                                                        )
                                                                        Text(
                                                                            text = callItem.duration + " seconds",
                                                                            fontSize = 16.sp,
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Text(text = "Swipe left for more call types")
                    }

                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Call Logs permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyCallLogsScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyCallLogsScreen
    }
}