package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.calendar

import android.graphics.pdf.PdfDocument.Page
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.Preconditions.checkArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.CalendarItem
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MyCalendar(
    drawerState: DrawerState,
    viewModel: MyCalendarViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf("android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR")
    )
    var isEventDetailsExpanded by remember { mutableStateOf(false) }
    var calendarlazyList by rememberSaveable { mutableStateOf(emptyList<CalendarItem>().toMutableList()) }
    var eventlazyList by rememberSaveable { mutableStateOf(emptyMap<Int, List<EventItem>>()) }
    val calendarPagerState = rememberPagerState(pageCount = { calendarlazyList.size })
    val eventPagerState = rememberPagerState(pageCount = { eventlazyList.size })

    AllPermissionsImplTheme {
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
                            text = "Calendars",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (multiplePermissionsState.allPermissionsGranted) {
                    if(calendarlazyList.isNullOrEmpty()) {
                        Button(onClick = {
                            calendarlazyList.clear()
                            viewModel.getCalendars(context)
                            calendarlazyList = viewModel.calendarList.toMutableList()
                        }) {
                            Text("Get Calendar Details")
                        }
                    } else {
                        HorizontalPager(
                            modifier = Modifier
                            .size(width = LocalConfiguration.current.screenWidthDp.dp,
                                height = LocalConfiguration.current.screenHeightDp.minus(300).dp),
                            state = calendarPagerState,
                            contentPadding = PaddingValues(1.dp),
                            pageSize = PageSize.Fixed(LocalConfiguration.current.screenWidthDp.minus(50).dp),
                            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(eventPagerState, Orientation.Vertical)
                        ) {index->
                            viewModel.getEvents(calendarlazyList[index].id.toString(), context)
                                eventlazyList += mapOf(Pair(calendarlazyList[index].id.toInt(), viewModel.calendarEventList))
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
                                        Text(text = calendarlazyList[index].displayName.toString(), fontSize = 20.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Justify, color = Color.Green)
                                        MyCalendarGroup(key = calendarlazyList[index].id.toString(), eventlazyList = eventlazyList)
                                    }
                                }
                        }
                        Text(text = "Swipe left for next calendar group")
                    }
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Calendar permission")
                    }
                }
            }
        }
    }
}

fun getDate(temp: Int): String {
    return if (temp in 11..13) {
        "th";
    } else when (temp % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
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
fun MyCalendarScreenPreview() {
    Screen.MyCalendarScreen
}