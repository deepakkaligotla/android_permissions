package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.musicAudio

import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
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
import java.io.File

@RequiresApi(34)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun MyMusicAudio(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyMusicAudioViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState("android.permission.READ_MEDIA_AUDIO")
    var musicAudioLazyList by rememberSaveable { mutableStateOf(emptyMap<String, List<File>>()) }
    var musicAudioExtensionsList by rememberSaveable { mutableStateOf(emptyList<String>()) }
    val extensionPagerState = rememberPagerState(pageCount = { musicAudioExtensionsList.size })
    val audioFilesPagerState = rememberPagerState(pageCount = { musicAudioLazyList.size })
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var selectedAudio: File? by remember { mutableStateOf(null) }

    AllPermissionsImplTheme(appTheme = userTheme) {
        Scaffold(
            modifier = Modifier.testTag("myTableTag"),
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(top = 5.dp)) {
                    Row {
                        Text(
                            text = "Music & Audio (${viewModel.audioMusicFilesList.size})",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (permissionState.status.isGranted) {
                    if (musicAudioLazyList.isEmpty()) {
                        musicAudioExtensionsList.toMutableList().clear()
                        viewModel.createCursor(context)
                        musicAudioExtensionsList = viewModel.isAudio

                        for(extension in musicAudioExtensionsList) {
                            if(viewModel.audioMusicFilesList.groupBy(keySelector = {it.name.substringAfterLast('.', "").contains(extension)})[true]!=null) {
                                musicAudioLazyList += mapOf(
                                    Pair(
                                        extension,
                                        viewModel.audioMusicFilesList.groupBy(keySelector = {it.name.substringAfterLast('.', "").contains(extension)})[true]!!
                                    )
                                )
                            }
                        }
                    } else {
                        VerticalPager(
                            modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.minus(240).dp).fillMaxWidth(),
                            state = extensionPagerState,
                            contentPadding = PaddingValues(1.dp),
                            pageSize = PageSize.Fixed(200.dp),
                            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                                audioFilesPagerState,
                                Orientation.Horizontal
                            )
                        ) { index ->
                            ElevatedCard(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(5.dp)
                                    .height(180.dp)
                                    .fillMaxWidth(),
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
                                        text = "${musicAudioExtensionsList[index]} files (${if (musicAudioLazyList[musicAudioExtensionsList[index]].isNullOrEmpty()) 0 else musicAudioLazyList[musicAudioExtensionsList[index]]!!.size})",
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(2.dp),
                                        textAlign = TextAlign.Justify,
                                        color = Color.Green
                                    )
                                    if(musicAudioLazyList[musicAudioExtensionsList[index]].isNullOrEmpty()) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "No ${musicAudioExtensionsList[index]} files found",
                                                style = MaterialTheme.typography.headlineSmall,
                                                modifier = Modifier.padding(2.dp),
                                                textAlign = TextAlign.Justify,
                                                color = Color.Red
                                            )
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            LazyHorizontalGrid(
                                                rows = GridCells.FixedSize(120.dp),
                                                contentPadding = PaddingValues(0.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                if (musicAudioLazyList.isNotEmpty()) {
                                                    if (musicAudioLazyList.containsKey(musicAudioExtensionsList[index])) {
                                                        items(musicAudioLazyList[musicAudioExtensionsList[index]]!!.toMutableList()) { item ->
                                                            ElevatedCard(
                                                                colors = CardDefaults.cardColors(
                                                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                                                ),
                                                                elevation = CardDefaults.cardElevation(
                                                                    defaultElevation = 6.dp
                                                                ),
                                                                modifier = Modifier
                                                                    .size(
                                                                        width = 120.dp,
                                                                        height = 120.dp
                                                                    )
                                                                    .padding(2.dp),
                                                                content = {
                                                                    Column(
                                                                        modifier = Modifier
                                                                            .padding(1.dp)
                                                                            .fillMaxWidth()
                                                                            .wrapContentSize(Alignment.Center)
                                                                            .testTag("Key"),
                                                                        verticalArrangement = Arrangement.Center,
                                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                                    ) {
                                                                        Box(
                                                                            modifier = Modifier
                                                                                .fillMaxSize()
                                                                                .padding(8.dp)
                                                                        ) {
                                                                            Box(
                                                                                modifier = Modifier
                                                                                    .shadow(
                                                                                        elevation = 4.dp,
                                                                                    )
                                                                                    .align(Alignment.TopStart)
                                                                            ) {
                                                                                Text(
                                                                                    item.name,
                                                                                    style = MaterialTheme.typography.bodySmall,
                                                                                    modifier = Modifier.padding(
                                                                                        10.dp
                                                                                    )
                                                                                )
                                                                            }
                                                                            Box(
                                                                                modifier = Modifier
                                                                                    .shadow(
                                                                                        elevation = 4.dp,
                                                                                        shape = CircleShape
                                                                                    )
                                                                                    .align(Alignment.BottomEnd)
                                                                                    .clip(CircleShape)
                                                                                    .selectable(
                                                                                        selected = selectedAudio == item,
                                                                                        onClick = {
                                                                                            selectedAudio = item
                                                                                            if (!isPlaying && !viewModel.isPlaying) {
                                                                                                viewModel.startPlaying(
                                                                                                    context,
                                                                                                    item
                                                                                                )
                                                                                            } else if (!isPlaying && viewModel.isPlaying) {
                                                                                                viewModel.stopPlaying()
                                                                                            } else if (isPlaying || viewModel.isPlaying) {
                                                                                                viewModel.startPlaying(
                                                                                                    context,
                                                                                                    item
                                                                                                )
                                                                                            }
                                                                                        }
                                                                                    )
                                                                            ) {
                                                                                if (selectedAudio == item) {
                                                                                    IconButton(
                                                                                        onClick = {
                                                                                            isPlaying = !isPlaying
                                                                                        },
                                                                                        modifier = Modifier
                                                                                            .size(48.dp)
                                                                                            .background(Color.Gray, CircleShape)
                                                                                    ) {
                                                                                        Icon(
                                                                                            imageVector = if (isPlaying) Icons.Default.PlayCircleOutline else Icons.Default.Stop,
                                                                                            contentDescription = "Play/Stop",
                                                                                            tint = Color.White,
                                                                                            modifier = Modifier.size(50.dp)
                                                                                        )
                                                                                    }
                                                                                } else {
                                                                                    Icon(
                                                                                        imageVector = Icons.Outlined.PlayCircleOutline,
                                                                                        contentDescription = "Play",
                                                                                        modifier = Modifier.size(
                                                                                            50.dp
                                                                                        )
                                                                                    )
                                                                                }
                                                                            }
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
                        }
                        Text(text = "Swipe down for more audio formats")
                    }
                } else {
                    Button(onClick = {
                        permissionState.launchPermissionRequest()
                    }) {
                        Text("Grant Music & Audio permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyMusicAudioScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyMusicAudioScreen
    }
}