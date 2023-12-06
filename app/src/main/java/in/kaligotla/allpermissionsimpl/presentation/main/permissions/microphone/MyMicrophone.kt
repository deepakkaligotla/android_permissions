package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone

import android.os.Environment
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.StopCircle
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.getDateTime
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import kotlinx.coroutines.Job
import java.io.File
import java.util.Date

@RequiresApi(34)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyMicrophone(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyMicrophoneViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = rememberPermissionState("android.permission.RECORD_AUDIO")
    val audioDir = File(context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS)?.path!!)
    var audioFile: File? by rememberSaveable { mutableStateOf(null) }
    var audioFiles by rememberSaveable { mutableStateOf(emptyList<File>()) }
    var playing by rememberSaveable { mutableStateOf(false) }
    var selectedAudio: File? by remember { mutableStateOf(null) }
    var isRecording by rememberSaveable { mutableStateOf(false) }
    var isPaused by rememberSaveable { mutableStateOf(false) }
    var audioRecorder by rememberSaveable { mutableStateOf<AudioRecorder?>(null) }
    var recordingDuration by rememberSaveable { mutableStateOf<String>("") }
    val visualizerJob by rememberSaveable { mutableStateOf<Job?>(null) }
    val amplitudeList by remember { mutableStateOf(mutableListOf<Int>()) }

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
                            text = "Microphone",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (permissionState.status.isGranted) {
                    audioFiles.toMutableList().clear()
                    viewModel.getAllRecordings(context)
                    audioFiles = viewModel.files.toMutableList()

                    IconButton(
                        onClick = {
                            if (isRecording) {
                                audioRecorder?.pauseRecording()
                                isPaused = true
                            } else {
                                if(isPaused) {
                                    audioRecorder?.resumeRecording()
                                    isPaused = false
                                } else {
                                    audioFile = File(audioDir, getDateTime(Date().time) + ".mp3")
                                    audioRecorder = AudioRecorder(
                                        context,
                                        audioFile!!,
                                        amplitudeList
                                    ) {
                                        visualizerJob
                                    }
                                    audioRecorder?.startRecording()
                                }
                            }
                            isRecording = !isRecording
                            audioRecorder?.recordingDuration?.observe(lifecycleOwner) {
                                recordingDuration = it
                            }
                        },
                        modifier = Modifier
                            .size(72.dp)
                            .background(if (isRecording) Color.Red else Color.Gray, CircleShape)
                    ) {
                        Icon(
                            imageVector = if(isRecording) Icons.Default.Pause else if(isPaused) Icons.Default.Mic else Icons.Default.Mic,
                            contentDescription = "Record/Stop",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    IconButton(onClick = {
                        audioRecorder?.stopRecording()
                        isPaused = false
                        isRecording = false
                    }) {
                        Icon(imageVector = Icons.Default.StopCircle, contentDescription = "stop", tint = Color.White,
                            modifier = Modifier.size(50.dp))
                    }

                    Text(recordingDuration, style = MaterialTheme.typography.headlineSmall)

                    DrawAudioWaves(amplitudeList)

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(
                            LocalConfiguration.current.screenWidthDp.dp
                        ),
                        userScrollEnabled = true,
                        modifier = Modifier
                            .padding(5.dp)
                            .height(LocalConfiguration.current.screenHeightDp.minus(490).dp),
                        contentPadding = PaddingValues(0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (audioFiles.isNotEmpty()) {
                            items(audioFiles.size, key = { file -> file }) { index ->
                                ElevatedCard(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 6.dp
                                    ),
                                    modifier = Modifier
                                        .padding(3.dp),
                                    content = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                                .padding(8.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .shadow(
                                                        elevation = 4.dp,
                                                    )
                                                    .align(Alignment.CenterStart)
                                            ) {
                                                Text(
                                                    audioFiles[index].name.replace(".mp3", "")
                                                        .replace("_", " "),
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    modifier = Modifier.padding(10.dp)
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
                                                        selected = selectedAudio == audioFiles[index],
                                                        onClick = {
                                                            selectedAudio = audioFiles[index]
                                                            if (!playing) {
                                                                viewModel.startPlaying(
                                                                    context,
                                                                    audioFiles[index]
                                                                )
                                                                playing = viewModel.player.isPlaying
                                                            } else {
                                                                viewModel.stopPlaying()
                                                                playing = viewModel.player.isPlaying
                                                            }
                                                        }
                                                    )
                                            ) {
                                                if (selectedAudio == audioFiles[index]) {
                                                    if (playing) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.PlayCircleOutline,
                                                            contentDescription = "Play",
                                                            modifier = Modifier.size(
                                                                50.dp
                                                            )
                                                        )
                                                    } else {
                                                        Icon(
                                                            imageVector = Icons.Outlined.StopCircle,
                                                            contentDescription = "Stop",
                                                            modifier = Modifier.size(
                                                                50.dp
                                                            )
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
                                )
                            }
                        }
                    }
                    Text(text = "Swipe down for more recordings")
                } else {
                    Button(onClick = {
                        permissionState.launchPermissionRequest()
                    }) {
                        Text("Grant Microphone permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyMicrophoneScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyMicrophoneScreen
    }
}